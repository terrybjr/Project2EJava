package application.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import application.data.HandleItemInf;

@Entity
@Table(name = "todo")
@NamedQueries ({
	@NamedQuery(name = "ToDo.findAll", query = " SELECT T FROM ToDo T"),
	@NamedQuery(name = "ToDo.byToDoId", query = " SELECT T FROM ToDo T WHERE t.id = :id"),
})
public class ToDo implements HandleItemInf {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String task;
	private Date dueDate;
	private boolean isCompleted;
	private Date dateCompleted;
	private Date dateCreated;

	@PrePersist
	private void init() {
		this.setDateCreated(Date.valueOf(LocalDate.now()));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long pId) {
		this.id = pId;
	}

	public String getTask() {
		return this.task;
	}

	public void setTask(final String pTask) {
		this.task = pTask;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final Date pDueDate) {
		this.dueDate = pDueDate;
	}

	public boolean isCompleted() {
		return this.isCompleted;
	}

	public void setCompleted(final boolean pIsCompleted) {
		this.isCompleted = pIsCompleted;
	}

	public Date getDateCompleted() {
		return this.dateCompleted;
	}

	public void setDateCompleted(final Date pDateCompleted) {
		this.dateCompleted = pDateCompleted;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(final Date pDateCreated) {
		this.dateCreated = pDateCreated;
	}

	public void copyFields(final ToDo item) {
		this.id = item.id;
		this.task = item.task;
		this.dueDate = item.dueDate;
		this.isCompleted = item.isCompleted;
		this.dateCompleted = item.dateCompleted;
		this.dateCreated = item.dateCreated;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		ToDo newToDo = (ToDo) pNewItem;
		this.copyFields(newToDo);
		return this;
	}

}
