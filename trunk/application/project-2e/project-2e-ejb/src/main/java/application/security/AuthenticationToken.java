package application.security;

public class AuthenticationToken {

	private String token;

	public AuthenticationToken() {

	}

	public String getToken() {
		return this.token;
	}

	public void setToken(final String token) {
		this.token = token;
	}
}
