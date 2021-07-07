package application.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.Character;
import application.entity.User;
import application.session.CharacterSLS;
import application.utils.DunGenLogger;
import application.utils.MiscUtils;

@Path(value = "character")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CharacterRest {
	@EJB
	CharacterSLS characterSLS;
	String className = this.getClass().getSimpleName();
	static Logger logger = DunGenLogger.getLogger();
	@Path("new/{name}/{userId}")
	@GET
	public Response createCharacter(@PathParam("name") final String name,
			@PathParam("userId") final Long userId) {
		String method = this.className + ".createCharacter: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "name: " + name);
			logger.debug(method + "userId: " + userId);
		}
		try {
			StatusResp resp = this.characterSLS.createCharacter(userId, name);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	@Path("setAncestry/{characterId}/{ancestry}")
	@GET
	public Response setAncestry(@PathParam("characterId") final Long characterId,
			@PathParam("ancestry") final String ancestry) {
		String method = this.className + ".setAncestry: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
			logger.debug(method + "characterId: " + characterId);
			logger.debug(method + "ancestry: " + ancestry);
		}
		try {
			StatusResp resp = this.characterSLS.setAncestry(characterId, ancestry);
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}

	// localhost:8080/project-2e-ws/api/v1/pc/list
	@Path("list")
	@GET
	public Response listCharacter() {
		String method = this.className + ".listCharacter: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "entering");
		}
		try {
			StatusResp resp = this.characterSLS.getCharacters();
			return MiscUtils.buildResponse(resp);
		} catch (Exception ex) {
			return MiscUtils.buildResponse(ex);
		}
	}
}
