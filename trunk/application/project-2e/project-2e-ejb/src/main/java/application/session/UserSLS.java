package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.cdi.beans.Global;
import application.data.StatusResp;
import application.entity.User;
import application.utils.DunGenLogger;

@Stateless
@EJB(name = "java:global/UserSLS", beanInterface = UserSLS.class)
@LocalBean
@RolesAllowed("USER")
public class UserSLS {
	@EJB
	PersistenceSLS persistenceSLS;
	@Context
	private UriInfo uriInfo;
	@Resource
	EJBContext securityContext;
	@Inject
	Global global;

	String className = this.getClass().getSimpleName();

	static Logger logger = DunGenLogger.getLogger();

	public StatusResp createUser()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		User newUser = new User();
		Optional<User> existingUser = this.getUserById(this.global.getUserId());
		if (existingUser.isPresent()) {
			return new StatusResp(StatusResp.STAT_USER, "User already has an account.");
		}
		newUser.setId(this.global.getUserId());
		return new StatusResp(this.persistenceSLS.persistUser(newUser));
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

	public StatusResp findUser(final String id) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<User> user = this.getUserById(id);
		return new StatusResp(user.get());
	}
	@PermitAll
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

	public Optional<User> getUserById(final String id) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData(User.QUERY_BY_ID, User.class, parameters, method);
	}


	@RolesAllowed("USER")
	public StatusResp userSecure() {
		if (this.securityContext == null) {
			logger.debug("It's null... noooo!!");
		} else {
			logger.debug(this.securityContext);
			logger.debug(this.securityContext.getCallerPrincipal());
			logger.debug(this.securityContext.getCallerPrincipal().getName());
		}
		return new StatusResp("Hello from userSecure");
	}

	public StatusResp adminSecure() {

		return new StatusResp("Hello from adminSecure");
	}
}
