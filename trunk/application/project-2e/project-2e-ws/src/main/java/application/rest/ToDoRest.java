package application.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import application.entity.ToDo;
import application.service.ToDoService;
import application.session.ProjectSLS;

@Path(value = "todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ToDoRest {
	@EJB
	ProjectSLS projectSLS;

	// api/v1/todo/new
	@Path("new")
	@POST
	public Response createTodo(final ToDo todo) {
		try {
			this.projectSLS.createToDo(todo);
		} catch (Exception ex) {
			System.out.println("Crap");
		}
		return Response.ok(todo).build();
	}

	// api/v1/todo/update
	@Path("update")
	@PUT
	public Response updateTodo(final ToDo todo) {
		try {
			ToDo retToDo = this.projectSLS.updateToDo(todo);
			return Response.ok(retToDo).build();
		} catch (Exception ex) {
			System.out.println("Crap");
			return Response.serverError().build();
		}
	}

	// api/v1/todo/1
	@Path("{id}")
	@GET
	public ToDo getTodo(@PathParam("id") final Long id) {
		return this.projectSLS.findToDo(id);
	}

	// api/v1/todo/list
	@Path("list")
	@GET
	public List<ToDo> listTodo() {
		return this.projectSLS.getToDos();
	}

	// api/v1/todo/status
	@Path("status")
	@POST
	public Response markAsComplete(@QueryParam("id") final Long id) {
		ToDo td = this.projectSLS.findToDo(id);
		td.setCompleted(true);
		td = this.projectSLS.updateToDo(td);
		return Response.ok(td).build();
	}

}
