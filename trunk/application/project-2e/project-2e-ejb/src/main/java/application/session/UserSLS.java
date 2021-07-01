package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.utils.DunGenLogger;

@Stateless
@EJB(name = "java:global/UserSLS", beanInterface = UserSLS.class)
@LocalBean
public class UserSLS {
	@EJB
	PersistenceSLS persistenceSLS;

	String className = this.getClass().getSimpleName();

	static Logger logger = DunGenLogger.getLogger();

	public User createUser(final User user)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// persist into db
		return this.persistenceSLS.persistUser(user);
		// return the persisted object to the client
	}

	public StatusResp createUser(final String email)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + ".createUser: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "name: " + email);
		}
		User user = new User();
		user.setEmail(email);
		user = this.persistenceSLS.persistUser(user);
		return new StatusResp(user);
	}

	public User updateUser(final User user) {
		Optional<User> UserOpt = this.getUserById(user.getId());
		User entUser = UserOpt.get();
		entUser.updateItem(user);
		return entUser;
	}

	public User findUser(final Long id) {
		Optional<User> user = this.getUserById(id);
		return user.get();
	}

	public StatusResp getUsers() {
		List<User> UserList = this.persistenceSLS.getDataList(User.queryByAll, User.class, null, "getUsers");
		return new StatusResp(UserList);
	}

	public Optional<User> getUserById(final Long id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData(User.queryById, User.class, parameters, "getUserById");
	}
}
