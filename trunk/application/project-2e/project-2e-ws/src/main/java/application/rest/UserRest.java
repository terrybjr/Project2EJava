package application.rest;

import java.util.List;

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
import application.session.UserSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;

@Path(value = "user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {
	@EJB
	UserSLS userSLS;
	String className = this.getClass().getSimpleName();
	static Logger logger = DunGenLogger.getLogger();

	// localhost:8080/project-2e-ws/api/v1/user/new
	@Path("new")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(final String email) {
		String method = this.className + ".createUser: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "email: " + email);
		}
		try {
			StatusResp resp = this.userSLS.createUser(email);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	// localhost:8080/project-2e-ws/api/v1/user/list
	@Path("list")
	@GET
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
