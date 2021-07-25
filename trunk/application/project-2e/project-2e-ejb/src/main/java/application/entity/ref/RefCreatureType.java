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
	@NamedQuery(name = "RefCreatureType.findAll", query = " SELECT T FROM RefCreatureType T"),
	@NamedQuery(name = "RefCreatureType.byName", query = " SELECT T FROM RefCreatureType T WHERE t.name = :name"),
})
public class RefCreatureType implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static String queryByAll = "RefCreatureType.findAll";
	@Transient
	public static String queryByName = "RefCreatureType.byName";

	@Id
	@Column(name = "Name", length = 50)
	private String name;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefCreatureType item) {
		this.setName(item.getName());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefCreatureType newItem = (RefCreatureType) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
