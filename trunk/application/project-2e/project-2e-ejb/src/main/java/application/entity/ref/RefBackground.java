package application.entity.ref;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;
import lombok.Data;

@Entity
@Data
@Table(name = "ref_Background")
@NamedQueries({ @NamedQuery(name = RefBackground.QUERY_BY_ALL, query = " SELECT T FROM RefBackground T"),
	@NamedQuery(name = RefBackground.QUERY_BY_NAME, query = " SELECT T FROM RefBackground T WHERE t.name = :name"), })
public class RefBackground implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String QUERY_BY_ALL = "RefBackground.findAll";
	@Transient
	public static final String QUERY_BY_NAME = "RefBackground.byName";

	@Id
	@Column(name = "Name", length = 50)
	private String name;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefBackground item) {
		this.setName(item.getName());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefBackground newItem = (RefBackground) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
