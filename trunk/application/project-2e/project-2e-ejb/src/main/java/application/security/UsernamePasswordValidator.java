package application.security;

import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import application.entity.User;
import application.security.exception.AuthenticationException;
import application.security.exception.PasswordEncoder;
import application.session.UserSLS;

@ApplicationScoped
public class UsernamePasswordValidator {

	@EJB
	private UserSLS userSLS;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Validate username and password.
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public User validateCredentials(final String email, final String password) {

		Optional<User> optUser = this.userSLS.getUserByEmail(email);

		if (!optUser.isPresent()) {
			// User cannot be found with the given username/email
			throw new AuthenticationException("Bad credentials.");
		}

		User user = optUser.get();

		if (!this.passwordEncoder.checkPassword(password, user.getPassword())) {
			// Invalid password
			throw new AuthenticationException("Bad credentials.");
		}

		return user;
	}
}