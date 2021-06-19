package application.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;

@Entity
@Table(name = "user")
@NamedQueries ({
	@NamedQuery(name = "User.findAll", query = " SELECT T FROM User T"),
	@NamedQuery(name = "User.byId", query = " SELECT T FROM User T WHERE t.id = :id"),
})
public class User implements HandleItemInf {
	@Transient
	public static String queryByAll = "User.findAll";
	@Transient
	public static String queryById = "User.byId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "user")
	private final  List<PlayerCharacter> characters;

	public User() {
		super();
		this.characters = null;
		// TODO Auto-generated constructor stub
	}

	public User(final Long pId, final String pName, final List<PlayerCharacter> pCharacters) {
		super();
		this.id = pId;
		this.name = pName;
		this.characters = pCharacters;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long pId) {
		this.id = pId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String pName) {
		this.name = pName;
	}

	public void copyFields(final User item) {
		this.id = item.id;
		this.setName(item.getName());
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		User newItem = (User) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
