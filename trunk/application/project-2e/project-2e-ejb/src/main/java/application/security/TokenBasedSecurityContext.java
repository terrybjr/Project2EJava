package application.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class TokenBasedSecurityContext implements SecurityContext {

	private AuthenticatedUserDetails authenticatedUserDetails;
	private AuthenticationTokenDetails authenticationTokenDetails;
	private final boolean secure;

	public TokenBasedSecurityContext(final AuthenticatedUserDetails authenticatedUserDetails,
			final AuthenticationTokenDetails authenticationTokenDetails, final boolean secure) {
		this.authenticatedUserDetails = authenticatedUserDetails;
		this.authenticationTokenDetails = authenticationTokenDetails;
		this.secure = secure;
	}

	@Override
	public Principal getUserPrincipal() {
		return this.authenticatedUserDetails;
	}

	@Override
	public boolean isUserInRole(final String s) {
		return this.authenticatedUserDetails.getAuthorities().contains(Authority.valueOf(s));
	}

	@Override
	public boolean isSecure() {
		return this.secure;
	}

	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}

	public AuthenticationTokenDetails getAuthenticationTokenDetails() {
		return this.authenticationTokenDetails;
	}
}
