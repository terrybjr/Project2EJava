package application.utils;

public abstract class Lookup {

	// static data fetch queries
	public static final String STATIC_DATA_ANCESTRY = "ancestry";
	public static final String STATIC_DATA_ALIGNMENT = "alignment";
	public static final String STATIC_DATA_SIZE = "size";
	public static final String STATIC_DATA_LANGUAGE = "language";

	public static String secret = "secret";

	/**
	 * Allowed clock skew for verifying the token signature (in seconds).
	 */
	// @Configurable("authentication.jwt.clockSkew")
	public static Long clockSkew = (long) 10;

	/**
	 * Identifies the recipients that the JWT token is intended for.
	 */
	// @Configurable("authentication.jwt.audience")
	public static String audience = "http://example.org";

	/**
	 * Identifies the JWT token issuer.
	 */
	// ("authentication.jwt.issuer")
	public static String issuer = "http://example.org";

	/**
	 * JWT claim for the authorities.
	 */
	// @Configurable("authentication.jwt.claimNames.authorities")
	public static String authoritiesClaimName = "authorities";

	/**
	 * JWT claim for the token refreshment count.
	 */
	// @Configurable("authentication.jwt.claimNames.refreshCount")
	public static String refreshCountClaimName = "refreshCount";

	/**
	 * JWT claim for the maximum times that a token can be refreshed.
	 */
	// @Configurable("authentication.jwt.claimNames.refreshLimit")
	public static String refreshLimitClaimName = "refreshLimit";

}
