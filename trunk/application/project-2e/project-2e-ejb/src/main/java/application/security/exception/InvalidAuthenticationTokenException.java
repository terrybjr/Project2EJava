package application.security.exception;

public class InvalidAuthenticationTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidAuthenticationTokenException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
