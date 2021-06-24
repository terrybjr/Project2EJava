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

import application.entity.ToDo;
import application.entity.User;
import application.session.UserSLS;
import application.utils.DunGenLogger;

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
	public Response createUser(final String name) {
		String method = this.className + ".createUser";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "name: " + name);
		}
		User user = null;
		try {
			user = this.userSLS.createUser(name);
		} catch (Exception ex) {
			System.out.println("Crap");
		}
		return Response.ok(user).build();
	}

	// localhost:8080/project-2e-ws/api/v1/user/list
	@Path("list")
	@GET
	public Response listUser() {
		return Response.ok(this.userSLS.getUsers()).build();
	}
}
