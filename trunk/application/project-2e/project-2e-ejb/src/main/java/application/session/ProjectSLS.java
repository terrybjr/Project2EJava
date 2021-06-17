package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.entity.ToDo;
import application.service.ToDoService;

@Stateless
@EJB(name = "java:global/ProjectSLS", beanInterface = ProjectSLS.class)
@LocalBean
public class ProjectSLS extends SLSBase {
	@EJB
	PersistenceSLS persistenceSLS;
	@Inject
	ToDoService service;

	public ToDo createToDo(final ToDo toDo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// persist into db
		return this.persistenceSLS.persistToDo(toDo);
		// return the persisted object to the client
	}

	public ToDo updateToDo(final ToDo toDo) {
		Optional<ToDo> toDoOpt = this.getToDoById(toDo.getId());
		ToDo entToDo = toDoOpt.get();
		entToDo.updateItem(toDo);
		entToDo.setCompleted(true);
		return entToDo;
	}

	public ToDo findToDo(final Long id) {
		Optional<ToDo> toDo = this.getToDoById(id);
		return toDo.get();
	}

	public List<ToDo> getToDos() {
		return this.service.getToDos();
	}

	public Optional<ToDo> getToDoById(final Long id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData("ToDo.byToDoId", ToDo.class, parameters, "getToDoById");
	}
}
