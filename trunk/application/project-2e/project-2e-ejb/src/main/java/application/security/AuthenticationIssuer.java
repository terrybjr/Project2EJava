package application.security;

import javax.enterprise.context.Dependent;
import application.utils.Lookup;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Component which provides operations for issuing JWT tokens.
 *
 * @author cassiomolin
 */
@Dependent
class AuthenticationTokenIssuer {

	/**
	 * Issue a JWT token
	 *
	 * @param authenticationTokenDetails
	 * @return
	 */
	public String issueToken(final AuthenticationTokenDetails authenticationTokenDetails) {

		return Jwts.builder()
				.setId(authenticationTokenDetails.getId())
				.setIssuer(Lookup.issuer).setAudience(Lookup.audience)
				.setSubject(authenticationTokenDetails.getEmail())
				.setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
				.setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
				.claim(Lookup.authoritiesClaimName, authenticationTokenDetails.getAuthorities())
				.claim(Lookup.refreshCountClaimName, authenticationTokenDetails.getRefreshCount())
				.claim(Lookup.refreshLimitClaimName, authenticationTokenDetails.getRefreshLimit())
				.signWith(SignatureAlgorithm.HS256, Lookup.secret)
				.compact();
	}
}