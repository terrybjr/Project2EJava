package application.rest;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.Logger;

import application.data.StaticData;
import application.data.StatusResp;
import application.entity.User;
import application.security.LoginDS;
import application.session.UserSLS;
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
	String className = this.getClass().getSimpleName();
	static Logger logger = DunGenLogger.getLogger();
	@EJB
	UserSLS userSLS;
	@Context
	private UriInfo uriInfo;
	@Context
	private SecurityContext securityContext;

	// localhost:8080/project-2e-ws/api/user/new
	@POST
	@Path("new")
	@ApiOperation(value = "Add a User")
	@ApiResponse(code = 200, message = "Success")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(final User user) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		try {
			StatusResp resp = this.userSLS.createUser(user);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	@POST
	@Path("login")
	@ApiOperation(value = "Login To A User")
	@ApiResponse(code = 200, message = "Success")
	@Produces(MediaType.TEXT_HTML)
	public Response login(final LoginDS login) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		try {
			StatusResp resp = this.userSLS.login(login);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	@GET
	@Path("getUser/{email}")
	@ApiOperation(value = "Get a User")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response getUser(
			@PathParam("email") final String email,
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		try {
			return MiscUtils.buildResponse(this.userSLS.getUserByEmailEntry(email));
		} catch (Exception e) {
			return MiscUtils.buildResponse(e);
		}
	}
	// localhost:8080/project-2e-ws/api/user/list
	@GET
	@Path("list")
	@ApiOperation(value = "List Users")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response listUser(
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		try {
			return MiscUtils.buildResponse(this.userSLS.getUsers());
		} catch (Exception e) {
			return MiscUtils.buildResponse(e);
		}
	}

	@GET
	@Secure
	@Path("adminSecure")
	@RolesAllowed("ADMIN")
	@ApiOperation(value = "AdminSecure")
	@Produces(MediaType.TEXT_HTML)
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response adminSecure(
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		logger.debug(this.securityContext.getUserPrincipal().getName());
		logger.debug(this.securityContext.isUserInRole("ADMIN"));
		return MiscUtils.buildResponse(new StatusResp("wohoo admin"));
	}

	@GET
	@Path("userSecure")
	@RolesAllowed("USER")
	@ApiOperation(value = "UserSecure")
	@Produces(MediaType.TEXT_HTML)
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response userSecure(
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		return MiscUtils.buildResponse(new StatusResp("wohoo admin and user"));
	}


	// keeping for reference
	//	public String getToken(final String email) {
	//		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
	//		if (logger.isDebugEnabled()) {
	//			logger.debug(method + "Entering");
	//		}
	//		Key key = MiscUtils.generateKey(email);
	//		if (logger.isDebugEnabled()) {
	//			logger.debug("email: " + email);
	//			logger.debug("uriInfo: " + this.uriInfo);
	//		}
	//		String token = Jwts.builder().setSubject(email).setIssuer(this.uriInfo.getAbsolutePath().toString())
	//				.setIssuedAt(new Date())
	//				.setExpiration(MiscUtils.toDate(LocalDateTime.now().plusMinutes(15)))
	//				.signWith(SignatureAlgorithm.HS512, key).setAudience(this.uriInfo.getBaseUri().toString()).compact();
	//		if (logger.isDebugEnabled()) {
	//			logger.debug(method + "token =" + token);
	//		}
	//		return token;
	//	}

}
