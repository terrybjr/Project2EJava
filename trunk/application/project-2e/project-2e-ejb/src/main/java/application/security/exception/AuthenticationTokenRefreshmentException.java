package application.security.exception;

public class AuthenticationTokenRefreshmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthenticationTokenRefreshmentException(final String message) {
		super(message);
	}

	public AuthenticationTokenRefreshmentException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
