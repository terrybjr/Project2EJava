package application.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.data.HandleItemInf;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Entity
@Data
@ApiModel
@Table(name = "User")
@NamedQueries ({
	@NamedQuery(name = User.QUERY_FIND_ALL, query = " SELECT T FROM User T"),
	@NamedQuery(name = User.QUERY_BY_ID, query = " SELECT T FROM User T WHERE t.id = :id"),
})
public class User implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_FIND_ALL = "User.findAll";
	@Transient
	public static final String QUERY_BY_ID = "User.byId";

	@Id
	@Column(name = "UserId")
	private String id;


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Character> characters;

	public void copyFields(final User item) {
		this.id = item.id;
	}

	public User() {
		super();
	}

	public User(final String stringObj) throws JsonMappingException, JsonProcessingException {
		super();
		this.copyFields(new ObjectMapper().readValue(stringObj, User.class));
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
