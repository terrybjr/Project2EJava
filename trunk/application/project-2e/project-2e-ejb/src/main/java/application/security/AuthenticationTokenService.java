package application.security;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import application.security.exception.AuthenticationTokenRefreshmentException;

/**
 * Service which provides operations for authentication tokens.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class AuthenticationTokenService {

	/**
	 * How long the token is valid for (in seconds).
	 */
	private Long validFor = (long) 36000;

	/**
	 * How many times the token can be refreshed.
	 */
	private Integer refreshLimit = 1;

	@Inject
	private AuthenticationTokenIssuer tokenIssuer;

	@Inject
	private AuthenticationTokenParser tokenParser;

	/**
	 * Issue a token for a user with the given authorities.
	 *
	 * @param username
	 * @param authorities
	 * @return
	 */
	public String issueToken(final String username, final Set<Authority> authorities) {

		String id = this.generateTokenIdentifier();
		ZonedDateTime issuedDate = ZonedDateTime.now();
		ZonedDateTime expirationDate = this.calculateExpirationDate(issuedDate);

		AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder().withId(id)
				.withUsername(username).withAuthorities(authorities).withIssuedDate(issuedDate)
				.withExpirationDate(expirationDate).withRefreshCount(0).withRefreshLimit(this.refreshLimit).build();

		return this.tokenIssuer.issueToken(authenticationTokenDetails);
	}

	/**
	 * Parse and validate the token.
	 *
	 * @param token
	 * @return
	 */
	public AuthenticationTokenDetails parseToken(final String token) {
		return this.tokenParser.parseToken(token);
	}

	/**
	 * Refresh a token.
	 *
	 * @param currentTokenDetails
	 * @return
	 */
	public String refreshToken(final AuthenticationTokenDetails currentTokenDetails) {

		if (!currentTokenDetails.isEligibleForRefreshment()) {
			throw new AuthenticationTokenRefreshmentException("This token cannot be refreshed");
		}

		ZonedDateTime issuedDate = ZonedDateTime.now();
		ZonedDateTime expirationDate = this.calculateExpirationDate(issuedDate);

		AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
				.withId(currentTokenDetails.getId()) // Reuse the same id
				.withUsername(currentTokenDetails.getEmail()).withAuthorities(currentTokenDetails.getAuthorities())
				.withIssuedDate(issuedDate).withExpirationDate(expirationDate)
				.withRefreshCount(currentTokenDetails.getRefreshCount() + 1).withRefreshLimit(this.refreshLimit)
				.build();

		return this.tokenIssuer.issueToken(newTokenDetails);
	}

	/**
	 * Calculate the expiration date for a token.
	 *
	 * @param issuedDate
	 * @return
	 */
	private ZonedDateTime calculateExpirationDate(final ZonedDateTime issuedDate) {
		return issuedDate.plusSeconds(this.validFor);
	}

	/**
	 * Generate a token identifier.
	 *
	 * @return
	 */
	private String generateTokenIdentifier() {
		return UUID.randomUUID().toString();
	}
}
