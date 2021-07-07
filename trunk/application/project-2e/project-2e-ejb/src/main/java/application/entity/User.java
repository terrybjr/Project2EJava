package application.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import application.data.HandleItemInf;
import io.swagger.annotations.ApiModel;

@Entity
@ApiModel
@Table(name = "User")
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@Column(name = "Email", unique = true, nullable = false, length = 150)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private final  List<Character> characters;

	public User() {
		super();
		this.characters = null;
		// TODO Auto-generated constructor stub
	}

	public User(final Long pId, final String pName, final List<Character> pCharacters) {
		super();
		this.id = pId;
		this.email = pName;
		this.characters = pCharacters;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long pId) {
		this.id = pId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String pEmail) {
		this.email = pEmail;
	}

	public void copyFields(final User item) {
		this.id = item.id;
		this.setEmail(item.getEmail());
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
