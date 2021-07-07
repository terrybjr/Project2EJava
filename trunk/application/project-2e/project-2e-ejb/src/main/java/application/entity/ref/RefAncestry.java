package application.entity.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;
import application.entity.Character;

@Entity
@Table(name = "ref_Ancestry")
@NamedQueries ({
	@NamedQuery(name = "RefAncestry.findAll", query = " SELECT T FROM RefAncestry T"),
	@NamedQuery(name = "RefAncestry.byName", query = " SELECT T FROM RefAncestry T WHERE t.name = :name"),
})
public final class RefAncestry implements HandleItemInf {
	@Transient
	public static String queryByAll = "RefAncestry.findAll";
	@Transient
	public static String queryByName = "RefAncestry.byName";

	/**
	 * Make this class unable to be created via code.
	 */
	private RefAncestry() {
		super();
	}

	@Id
	@Column(name = "Name", length = 50)
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String pName) {
		this.name = pName;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefAncestry item) {
		this.setName(item.getName());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefAncestry newItem = (RefAncestry) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
