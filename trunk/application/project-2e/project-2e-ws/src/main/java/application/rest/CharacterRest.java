package application.rest;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import application.cdi.annotations.DunGenRest;
import application.data.StatusResp;
import application.session.CharacterSLS;
import application.session.UserSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@DunGenRest
@Path(value = "character")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CharacterRest {
	@EJB
	CharacterSLS characterSLS;
	@EJB
	UserSLS userSLS;
	String className = this.getClass().getSimpleName();
	static Logger logger = DunGenLogger.getLogger();

	@Path("new/{userId}/{name}")
	@GET
	public Response createCharacter(@PathParam("name") final String name)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + ".createCharacter: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "name: " + name);
		}
		StatusResp resp = this.characterSLS.createCharacter(name);
		return MiscUtils.buildResponse(resp);
	}

	@GET
	@Path("list")
	@ApiOperation(value = "List Users")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success") })
	public Response listUser(@Context final HttpServletRequest request, @Context final HttpServletResponse response) {
		String method = this.className + "." + new Throwable().getStackTrace()[0].getMethodName() + ": ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		return MiscUtils.buildResponse(this.userSLS.getUsers());
	}

	@Path("setAncestry/{characterId}/{ancestry}")
	@GET
	public Response setAncestry(@PathParam("characterId") final Long characterId,
			@PathParam("ancestry") final String ancestry) throws Exception {
		String method = this.className + ".setAncestry: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "characterId: " + characterId);
			logger.debug(method + "ancestry: " + ancestry);
		}
		StatusResp resp = this.characterSLS.setAncestry(characterId, ancestry);
		return MiscUtils.buildResponse(resp);
	}

	// localhost:8080/project-2e-ws/api/v1/pc/list
	@Path("list")
	@GET
	public Response listCharacter() {
		String method = this.className + ".listCharacter: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
		}
		StatusResp resp = this.characterSLS.getCharacters();
		return MiscUtils.buildResponse(resp);
	}
}
