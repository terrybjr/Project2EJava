package application.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import javax.ws.rs.Priorities;
import java.util.Set;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import application.entity.User;
import application.session.UserSLS;

@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@EJB
	private UserSLS userSLS;

	@Inject
	private AuthenticationTokenService authenticationTokenService;

	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {

		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String authenticationToken = authorizationHeader.substring(7);
			this.handleTokenBasedAuthentication(authenticationToken, requestContext);
			return;
		}

		// Other authentication schemes (such as Basic) could be supported
	}

	private void handleTokenBasedAuthentication(final String authenticationToken,
			final ContainerRequestContext requestContext) {

		AuthenticationTokenDetails authenticationTokenDetails = this.authenticationTokenService
				.parseToken(authenticationToken);
		Optional<User> optUser = this.userSLS.getUserByEmail(authenticationTokenDetails.getEmail());
		if (!optUser.isPresent()) {

		}
		User user = optUser.get();
		Set<Authority> authoritySet = new HashSet<Authority>();
		authoritySet.add(Authority.ADMIN);
		AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getEmail(),
				authoritySet);

		boolean isSecure = requestContext.getSecurityContext().isSecure();
		SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails,
				authenticationTokenDetails, isSecure);
		requestContext.setSecurityContext(securityContext);
	}
}