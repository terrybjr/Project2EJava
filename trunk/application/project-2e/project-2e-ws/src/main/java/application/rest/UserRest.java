package application.rest;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.EJB;
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

import application.cdi.annotations.DunGenRest;
import application.data.StatusResp;
import application.session.UserSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@DunGenRest
//@Secure
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
	public Response createUser(
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response
			)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		StatusResp resp = this.userSLS.createUser();
		return MiscUtils.buildResponse(resp);
	}


	@GET
	@Path("getUser/{email}")
	@ApiOperation(value = "Get a User")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response getUser(
			@PathParam("id") final String id,
			@Context final HttpServletRequest request,
			@Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		return MiscUtils.buildResponse(this.userSLS.findUser(id));
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
		return MiscUtils.buildResponse(this.userSLS.getUsers());
	}

	@GET
	@Path("adminSecure")
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
		return MiscUtils.buildResponse(this.userSLS.adminSecure());
	}

	@GET
	@Path("userSecure")
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
		if (this.securityContext != null) {
			logger.debug("userPrincipal Name" + this.securityContext.getUserPrincipal().getName());
			logger.debug(this.securityContext.isUserInRole("USER"));
		}
		if (request.getRemoteUser() != null) {
			logger.debug("remoteUser" + request.getRemoteUser());
		}
		return MiscUtils.buildResponse(this.userSLS.userSecure());
	}

}
