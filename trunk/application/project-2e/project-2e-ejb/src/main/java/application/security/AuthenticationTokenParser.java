package application.security;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;

import application.security.exception.InvalidAuthenticationTokenException;
import application.utils.Lookup;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.stream.Collectors;

/**
 * Component which provides operations for parsing JWT tokens.
 *
 * @author cassiomolin
 */
@Dependent
class AuthenticationTokenParser {

	/**
	 * Parse a JWT token.
	 *
	 * @param token
	 * @return
	 */
	public AuthenticationTokenDetails parseToken(final String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(Lookup.secret).requireAudience(Lookup.audience)
					.setAllowedClockSkewSeconds(Lookup.clockSkew).parseClaimsJws(token).getBody();

			return new AuthenticationTokenDetails.Builder().withId(this.extractTokenIdFromClaims(claims))
					.withUsername(this.extractUsernameFromClaims(claims))
					.withAuthorities(this.extractAuthoritiesFromClaims(claims))
					.withIssuedDate(this.extractIssuedDateFromClaims(claims))
					.withExpirationDate(this.extractExpirationDateFromClaims(claims))
					.withRefreshCount(this.extractRefreshCountFromClaims(claims))
					.withRefreshLimit(this.extractRefreshLimitFromClaims(claims)).build();

		} catch (UnsupportedJwtException e) {
			throw new InvalidAuthenticationTokenException("Expired token", e);
		} catch (MalformedJwtException e) {
			throw new InvalidAuthenticationTokenException("Expired token", e);
		} catch (IllegalArgumentException e) {
			throw new InvalidAuthenticationTokenException("Expired token", e);
		} catch (ExpiredJwtException e) {
			throw new InvalidAuthenticationTokenException("Expired token", e);
		} catch (InvalidClaimException e) {
			throw new InvalidAuthenticationTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
		} catch (Exception e) {
			throw new InvalidAuthenticationTokenException("Invalid token", e);
		}

	}

	/**
	 * Extract the token identifier from the token claims.
	 *
	 * @param claims
	 * @return Identifier of the JWT token
	 */
	private String extractTokenIdFromClaims(@NotNull final Claims claims) {
		return (String) claims.get(Claims.ID);
	}

	/**
	 * Extract the username from the token claims.
	 *
	 * @param claims
	 * @return Username from the JWT token
	 */
	private String extractUsernameFromClaims(@NotNull final Claims claims) {
		return claims.getSubject();
	}

	/**
	 * Extract the user authorities from the token claims.
	 *
	 * @param claims
	 * @return User authorities from the JWT token
	 */
	private Set<Authority> extractAuthoritiesFromClaims(@NotNull final Claims claims) {
		List<String> rolesAsString = (List<String>) claims.getOrDefault(Lookup.authoritiesClaimName,
				new ArrayList<>());
		return rolesAsString.stream().map(Authority::valueOf).collect(Collectors.toSet());
	}

	/**
	 * Extract the issued date from the token claims.
	 *
	 * @param claims
	 * @return Issued date of the JWT token
	 */
	private ZonedDateTime extractIssuedDateFromClaims(@NotNull final Claims claims) {
		return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Extract the expiration date from the token claims.
	 *
	 * @param claims
	 * @return Expiration date of the JWT token
	 */
	private ZonedDateTime extractExpirationDateFromClaims(@NotNull final Claims claims) {
		return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Extract the refresh count from the token claims.
	 *
	 * @param claims
	 * @return Refresh count from the JWT token
	 */
	private int extractRefreshCountFromClaims(@NotNull final Claims claims) {
		return (int) claims.get(Lookup.refreshCountClaimName);
	}

	/**
	 * Extract the refresh limit from the token claims.
	 *
	 * @param claims
	 * @return Refresh limit from the JWT token
	 */
	private int extractRefreshLimitFromClaims(@NotNull final Claims claims) {
		return (int) claims.get(Lookup.refreshLimitClaimName);
	}
}