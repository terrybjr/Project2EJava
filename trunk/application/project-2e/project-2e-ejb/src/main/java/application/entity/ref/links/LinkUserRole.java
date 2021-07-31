package application.entity.ref.links;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.data.HandleItemInf;
import application.entity.User;
import application.entity.composite_key.LinkAncestryLanguageKey;
import application.entity.composite_key.LinkUserRoleKey;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefLanguage;
import application.entity.ref.RefRole;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = { "user", "role" })
@NamedQueries({ @NamedQuery(name = LinkUserRole.QUERY_BY_ALL, query = " SELECT T FROM LinkUserRole T"), })
@Table(name = "User_Role")
public class LinkUserRole implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "LinkUserRole.findAll";

	@EmbeddedId
	private LinkUserRoleKey key;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "UserId")
	@JsonBackReference
	private User user;

	@ManyToOne
	@MapsId("role")
	@JoinColumn(name = "Role")
	private RefRole role;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getKey";
	}

	public void copyFields(final LinkUserRole item) {
		this.key = item.key;
		this.role = item.role;
		this.user = item.user;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkUserRole newItem = (LinkUserRole) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
