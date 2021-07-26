package application.rest;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.session.UserSLS;
import application.state.ApplicationState;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import application.utils.Secure;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path(value = "user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(tags = { "UserRest" })
public class UserRest {
	@EJB
	UserSLS userSLS;
	@Context
	private UriInfo uriInfo;
	@Inject
	ApplicationState applicationState;
	String className = this.getClass().getSimpleName();
	static Logger logger = DunGenLogger.getLogger();

	// localhost:8080/project-2e-ws/api/user/new
	@Path("new")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add a User")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success")
	})
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "user: " + user);
			logger.debug("uriInfo: " + this.uriInfo);
		}
		try {
			StatusResp resp = this.userSLS.createUser(user);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	@Path("login")
	@POST
	@ApiOperation(value = "Login To A User")
	@ApiResponse(code = 200, message = "Success")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		try {
			StatusResp resp = this.userSLS.login(user);
			if (resp.statusOK()) {
				this.applicationState.setEmail(user.getEmail());
				String token = this.getToken(user.getEmail());
				return MiscUtils.buildResponse(resp, token);
			}
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	// localhost:8080/project-2e-ws/api/user/list
	@Path("list")
	@GET
	@ApiOperation(value = "List Users")
	@Secure
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response listUser() {
		String method = this.className + ".listUser: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
		}
		try {
			return MiscUtils.buildResponse(this.userSLS.getUsers());
		} catch (Exception e) {
			return MiscUtils.buildResponse(e);
		}
	}

	public String getToken(final String email) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Key key = MiscUtils.generateKey(email);
		if (logger.isDebugEnabled()) {
			logger.debug("email: " + email);
			logger.debug("uriInfo: " + this.uriInfo);
		}
		String token = Jwts.builder().setSubject(email).setIssuer(this.uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.setExpiration(MiscUtils.toDate(LocalDateTime.now().plusMinutes(15)))
				.signWith(SignatureAlgorithm.HS512, key).setAudience(this.uriInfo.getBaseUri().toString()).compact();
		if (logger.isDebugEnabled()) {
			logger.debug(method + "token =" + token);
		}
		return token;
	}
}
