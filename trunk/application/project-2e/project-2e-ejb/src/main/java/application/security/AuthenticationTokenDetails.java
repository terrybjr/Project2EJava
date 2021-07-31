package application.security;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

/**
 * Model that holds details about an authentication token.
 *
 */
public final class AuthenticationTokenDetails {

	private final String id;
	private final String email;
	private final Set<Authority> authorities;
	private final ZonedDateTime issuedDate;
	private final ZonedDateTime expirationDate;
	private final int refreshCount;
	private final int refreshLimit;

	private AuthenticationTokenDetails(final String id, final String email, final Set<Authority> authorities,
			final ZonedDateTime issuedDate, final ZonedDateTime expirationDate, final int refreshCount,
			final int refreshLimit) {
		this.id = id;
		this.email = email;
		this.authorities = authorities;
		this.issuedDate = issuedDate;
		this.expirationDate = expirationDate;
		this.refreshCount = refreshCount;
		this.refreshLimit = refreshLimit;
	}

	public String getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	public ZonedDateTime getIssuedDate() {
		return this.issuedDate;
	}

	public ZonedDateTime getExpirationDate() {
		return this.expirationDate;
	}

	public int getRefreshCount() {
		return this.refreshCount;
	}

	public int getRefreshLimit() {
		return this.refreshLimit;
	}

	/**
	 * Check if the authentication token is eligible for refreshment.
	 *
	 * @return
	 */
	public boolean isEligibleForRefreshment() {
		return this.refreshCount < this.refreshLimit;
	}

	/**
	 * Builder for the {@link AuthenticationTokenDetails}.
	 */
	public static class Builder {

		private String id;
		private String username;
		private Set<Authority> authorities;
		private ZonedDateTime issuedDate;
		private ZonedDateTime expirationDate;
		private int refreshCount;
		private int refreshLimit;

		public Builder withId(final String id) {
			this.id = id;
			return this;
		}

		public Builder withUsername(final String username) {
			this.username = username;
			return this;
		}

		public Builder withAuthorities(final Set<Authority> authorities) {
			this.authorities = Collections.unmodifiableSet(authorities == null ? new HashSet<>() : authorities);
			return this;
		}

		public Builder withIssuedDate(final ZonedDateTime issuedDate) {
			this.issuedDate = issuedDate;
			return this;
		}

		public Builder withExpirationDate(final ZonedDateTime expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder withRefreshCount(final int refreshCount) {
			this.refreshCount = refreshCount;
			return this;
		}

		public Builder withRefreshLimit(final int refreshLimit) {
			this.refreshLimit = refreshLimit;
			return this;
		}

		public AuthenticationTokenDetails build() {
			return new AuthenticationTokenDetails(this.id, this.username, this.authorities, this.issuedDate,
					this.expirationDate, this.refreshCount, this.refreshLimit);
		}
	}
}
