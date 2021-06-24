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

import application.entity.PlayerCharacter;
import application.entity.User;
import application.session.PlayerCharacterSLS;

@Path(value = "pc")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerCharacterRest {
	@EJB
	PlayerCharacterSLS pcSLS;

	// localhost:8080/project-2e-ws/api/v1/pc/new/jimbo/1
	@Path("new/{name}/{userId}")
	@GET
	public Response createPlayerCharacter(@PathParam("name") final String name,
			@PathParam("userId") final Long userId) {
		PlayerCharacter pc = null;
		System.out.println("HERE THEY ARE: " + name + " " + userId);
		try {
			pc = this.pcSLS.createPlayerCharacter(userId, name);
		} catch (Exception ex) {
			System.out.println("Crap");
		}
		return Response.ok(pc).build();
	}

	// localhost:8080/project-2e-ws/api/v1/pc/list
	@Path("list")
	@GET
	public Response listPlayerCharacter() {
		return Response.ok(this.pcSLS.getPlayerCharacters()).build();
	}

}
