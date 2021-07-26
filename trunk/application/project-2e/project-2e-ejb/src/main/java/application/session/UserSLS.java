package application.session;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.state.ApplicationState;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import application.utils.UtilDate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Stateless
@EJB(name = "java:global/UserSLS", beanInterface = UserSLS.class)
@LocalBean
public class UserSLS {
	@EJB
	PersistenceSLS persistenceSLS;
	@Inject
	ApplicationState applicationState;
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
		System.err.println(logger);
		return new StatusResp(this.persistenceSLS.persistUser(newUser));
	}

	public StatusResp login(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		System.out.println(method + "entering");

		// get user if exists
		Optional<User> optUser = this.getUserById(user.getId());

		if (!optUser.isPresent()) {
			return new StatusResp(StatusResp.STAT_UNAUTHORIZED, "Invalid Credentials");
		}

		User foundUser = optUser.get();
		System.out.println(method + "foundUser: " + foundUser);

		if (MiscUtils.authenticateUser(foundUser, user.getPassword())) {
			this.applicationState.setEmail(user.getEmail());
			String token = this.getToken(user.getEmail());
			return new StatusResp(token);
		}

		return new StatusResp(StatusResp.STAT_UNAUTHORIZED, "Invalid Credentials");
	}

	public String getToken(final String email) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Key key = MiscUtils.generateKey(email);
		String token = Jwts.builder().setSubject(email).setIssuer(this.uriInfo.getAbsolutePath().toString())
				.setIssuedAt(UtilDate.now().toSqlDate())
				.setExpiration(new UtilDate(LocalDateTime.now().plusMinutes(15).toLocalDate()).toSqlDate())
				.signWith(SignatureAlgorithm.HS512, key).setAudience(this.uriInfo.getBaseUri().toString())
				.compact();
		if (logger.isDebugEnabled()) {
			logger.debug(method + "token =" + token);
		}
		return token;
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
		List<User> UserList = this.persistenceSLS.getDataList(User.QUERY_FIND_ALL, User.class, null, "getUsers");
		return new StatusResp(UserList);
	}

	public Optional<User> getUserById(final Long id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData(User.QUERY_BY_ID, User.class, parameters, "getUserById");
	}
}
