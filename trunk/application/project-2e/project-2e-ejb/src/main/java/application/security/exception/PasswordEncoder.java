package application.security.exception;

import javax.enterprise.context.ApplicationScoped;

import at.favre.lib.crypto.bcrypt.BCrypt;

@ApplicationScoped
public class PasswordEncoder {

	/**
	 * Hashes a password using BCrypt.
	 *
	 * @param plainTextPassword
	 * @return
	 */
	public String hashPassword(final String plainTextPassword) {

		return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
	}

	/**
	 * Checks a password against a stored hash using BCrypt.
	 *
	 * @param plainTextPassword
	 * @param hashedPassword
	 * @return
	 */
	public boolean checkPassword(final String plainTextPassword, final String hashedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword);
		return result.verified;
	}
}
