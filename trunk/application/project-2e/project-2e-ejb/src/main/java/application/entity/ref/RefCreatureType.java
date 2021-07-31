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
@Table(name = "ref_CreatureType")
@NamedQueries ({
	@NamedQuery(name = RefCreatureType.QUERY_BY_ALL, query = " SELECT T FROM RefCreatureType T"),
	@NamedQuery(name = RefCreatureType.QUERY_BY_NAME, query = " SELECT T FROM RefCreatureType T WHERE t.name = :name"),
})
public class RefCreatureType implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "RefCreatureType.findAll";
	@Transient
	public static final String QUERY_BY_NAME = "RefCreatureType.byName";

	@Id
	@Column(name = "Name", length = 50)
	private String name;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefCreatureType item) {
		this.name = item.name;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefCreatureType newItem = (RefCreatureType) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
