package application.utils;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;

import application.state.ApplicationState;
import io.jsonwebtoken.Jwts;

@Provider
@Secure
@Priority(value = 1)
public class SecurityFilter implements ContainerRequestFilter {
	public static final String BEARER = "Bearer";

	static Logger logger = DunGenLogger.getLogger();

	@Inject
	ApplicationState applicationState;

	@Override
	public void filter(final ContainerRequestContext reqCtx) throws IOException {

		// 1. Get the token from the request header
		String authHeader = reqCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith(BEARER)) {

			logger.error("Wrong or no authorization header found: " + authHeader);

			throw new NotAuthorizedException("No authorization header provided");
		}
		String token = authHeader.substring(BEARER.length()).trim();

		// 2. Parse the token
		try {
			Key key = MiscUtils.generateKey(this.applicationState.getEmail());
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			SecurityContext securityContext = reqCtx.getSecurityContext();
			reqCtx.setSecurityContext(new SecurityContext() {
				@Override
				public Principal getUserPrincipal() {
					return () -> Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();

				}

				@Override
				public boolean isUserInRole(final String s) {
					return securityContext.isUserInRole(s);
				}

				@Override
				public boolean isSecure() {
					return securityContext.isSecure();
				}

				@Override
				public String getAuthenticationScheme() {
					return securityContext.getAuthenticationScheme();
				}
			});
			logger.info("Token parsed successfully");

			// 3. If parsing fails, yell.
		} catch (Exception e) {
			logger.error("Invalid token: " + token);
			// Another way to send exceptions to the client
			reqCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}

	}

}
