package application.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import application.entity.ToDo;

@Transactional // turned this into a service lets make some CRUD operations.
@SessionScoped
public class ToDoService implements Serializable {

	@PersistenceContext
	EntityManager em;

	public ToDo createToDo(final ToDo toDo) {
		// persist into db
		this.em.persist(toDo);
		// return the persisted object to the client
		return toDo;
	}

	public ToDo updateToDo(final ToDo toDo) {
		this.em.merge(toDo);
		return toDo;
	}

	public ToDo findToDo(final Long id) {
		return this.em.find(ToDo.class, id);
	}

	public List<ToDo> getToDos() {
		return this.em.createQuery("select t from ToDo t", ToDo.class).getResultList();
	}

}