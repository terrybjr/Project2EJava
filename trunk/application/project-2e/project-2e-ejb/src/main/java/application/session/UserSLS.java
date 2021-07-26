package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;

@Stateless
@EJB(name = "java:global/UserSLS", beanInterface = UserSLS.class)
@LocalBean
public class UserSLS {
	@EJB
	PersistenceSLS persistenceSLS;
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
		// persist into db
		Map<String, String> credMap = MiscUtils.hashPassword(user.getPassword());
		User newUser = new User();
		user.setPassword(credMap.get("hashedPassword"));
		user.setSalt(credMap.get("salt"));
		newUser.copyFields(user);
		newUser.setId(null);
		return new StatusResp(this.persistenceSLS.persistUser(newUser));
	}

	public StatusResp login(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		// get user if exists
		Optional<User> optUser = this.getUserByEmail(user.getEmail());

		if (!optUser.isPresent()) {
			return new StatusResp(StatusResp.STAT_UNAUTHORIZED, "Invalid Credentials");
		}

		User foundUser = optUser.get();
		if (logger.isDebugEnabled()) {
			logger.debug(method + "foundUser: " + foundUser);
		}

		if (MiscUtils.authenticateUser(foundUser, user.getPassword())) {
			return new StatusResp();
		}

		return new StatusResp(StatusResp.STAT_UNAUTHORIZED, "Invalid Credentials");
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
		return new StatusResp(UserList);
	}

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
		return this.persistenceSLS.getData(User.QUERY_BY_EMAIL, User.class, parameters, method);
	}
}
