package application.security.exception;


public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccessDeniedException(final String message) {
		super(message);
	}

	public AccessDeniedException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
