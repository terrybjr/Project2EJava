package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.security.AuthenticationToken;
import application.security.AuthenticationTokenService;
import application.security.Authority;
import application.security.LoginDS;
import application.security.UsernamePasswordValidator;
import application.security.exception.PasswordEncoder;
import application.utils.DunGenLogger;

@Stateless
@EJB(name = "java:global/UserSLS", beanInterface = UserSLS.class)
@LocalBean
public class UserSLS {
	@EJB
	PersistenceSLS persistenceSLS;
	@Inject
	private PasswordEncoder passwordEncoder;
	@Inject
	UsernamePasswordValidator validator;
	@Inject
	AuthenticationTokenService authService;
	@Context
	private UriInfo uriInfo;

	String className = this.getClass().getSimpleName();

	static Logger logger = DunGenLogger.getLogger();

	public StatusResp createUser(final User user)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		User newUser = new User();
		user.setPassword(this.passwordEncoder.hashPassword(user.getPassword()));
		// user.setSalt(credMap.get("salt"));
		newUser.copyFields(user);
		newUser.setId(null);
		return new StatusResp(this.persistenceSLS.persistUser(newUser));
	}

	public StatusResp login(final LoginDS login) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		User validUser = this.validator.validateCredentials(login.getEmail(), login.getPassword());
		String token = this.authService.issueToken(validUser);
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setToken(token);
		return new StatusResp(token);
	}

	public StatusResp getUserByEmailEntry(final String email) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<User> user = this.getUserByEmail(email);
		if (!user.isPresent()) {
			return new StatusResp(StatusResp.STAT_USER, "Unable to find user for email: " + email);
		}
		return new StatusResp(user.get());
	}

	public User updateUser(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<User> UserOpt = this.getUserById(user.getId());
		User entUser = UserOpt.get();
		entUser.updateItem(user);
		return entUser;
	}

	public User findUser(final Long id) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<User> user = this.getUserById(id);
		return user.get();
	}

	public StatusResp getUsers() {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		List<User> UserList = this.persistenceSLS.getDataList(User.QUERY_FIND_ALL, User.class, null, "getUsers");

		// forcing ORM to load everything;
		UserList.toString();
		return new StatusResp(UserList);
	}

	/**
	 * Call this method if you intend on returning user(s) to the front end, we need
	 * to populate dependencies before the entity becomes detached. This will
	 * forcfully pull in dependencies into the context
	 */

	public Optional<User> getUserById(final Long id) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData(User.QUERY_BY_ID, User.class, parameters, method);
	}

	public Optional<User> getUserByEmail(final String email) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering, email: " + email);
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		Optional<User> userOpt = this.persistenceSLS.getData(User.QUERY_BY_EMAIL, User.class, parameters, method);
		if (userOpt.isPresent()) {
			userOpt.get().toString();
		}
		return userOpt;
	}
}
