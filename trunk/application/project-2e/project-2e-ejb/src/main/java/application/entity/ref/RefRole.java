package application.entity.ref;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;
import application.security.Authority;
import lombok.Data;

@Entity
@Data
@Table(name = "ref_Role")
@NamedQueries({ @NamedQuery(name = RefRole.QUERY_BY_ALL, query = " SELECT T FROM RefRole T"), })
public class RefRole implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String QUERY_BY_ALL = "RefRole.findAll";

	@Id
	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	private Authority role;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getRole";
	}

	public void copyFields(final RefRole item) {
		this.role = item.role;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefRole newItem = (RefRole) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
