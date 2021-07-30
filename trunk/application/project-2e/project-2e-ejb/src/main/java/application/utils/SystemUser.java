package application.utils;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import application.security.Authority;

public final class SystemUser implements Principal {

	private String username;
	private Set<Authority> authorities;

	public void AuthenticatedUserDetails(final String username, final Set<Authority> authorities) {
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

