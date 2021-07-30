package application.security;

import java.security.Principal;
import java.util.Set;
import java.util.Collections;

public final class AuthenticatedUserDetails implements Principal {

	private final String username;
	private final Set<Authority> authorities;

	public AuthenticatedUserDetails(final String username, final Set<Authority> authorities) {
		this.username = username;
		this.authorities = Collections.unmodifiableSet(authorities);
	}

	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getName() {
		return this.username;
	}
}
