package application.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.data.HandleItemInf;
import application.entity.ref.RefRole;
import application.entity.ref.links.LinkAncestryLanguage;
import application.entity.ref.links.LinkUserRole;
import application.security.Authority;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Entity
@Data
@ApiModel
@Table(name = "User")
@NamedQueries ({
	@NamedQuery(name = User.QUERY_FIND_ALL, query = " SELECT T FROM User T"),
	@NamedQuery(name = User.QUERY_BY_ID, query = " SELECT T FROM User T WHERE t.id = :id"),
	@NamedQuery(name = User.QUERY_BY_EMAIL, query = " SELECT T FROM User T WHERE t.email = :email"),
	@NamedQuery(name = User.QUERY_JOIN_ROLES, query = " SELECT DISTINCT T FROM User T LEFT JOIN FETCH T.roles"),
})
public class User implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_FIND_ALL = "User.findAll";
	@Transient
	public static final String QUERY_BY_ID = "User.byId";
	@Transient
	public static final String QUERY_BY_EMAIL = "User.byEmail";
	@Transient
	public static final String QUERY_JOIN_ROLES = "User.joinRoles";

	@Id
	@Column(name = "UserId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@Column(name = "Email", unique = true, nullable = false, length = 150)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Character> characters;

	@Column(name = "Password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LinkUserRole> roles;

	public void copyFields(final User item) {
		this.id = item.id;
		this.password = item.password;
		this.roles = item.roles;
		this.email = item.email;
	}

	public User() {
		super();
	}

	public User(final String stringObj) throws JsonMappingException, JsonProcessingException {
		super();
		this.copyFields(new ObjectMapper().readValue(stringObj, User.class));
	}

	@JsonIgnore
	public Set<Authority> getRolesAsSet() {
		Set<Authority> authSet = new HashSet<Authority>();
		for (LinkUserRole role : this.getRoles()) {
			authSet.add(role.getKey().getRole());
		}
		System.err.println(this);
		return authSet;
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
