package application.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.User;
import application.session.UserSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
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
		logger.debug("apples");
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "user: " + user);
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
		System.out.println(method + "entering");
		try {
			StatusResp resp = this.userSLS.login(user);
			if(resp.allOK()) {
				return MiscUtils.buildResponse(resp, (String) resp.getRetObj());
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
}
