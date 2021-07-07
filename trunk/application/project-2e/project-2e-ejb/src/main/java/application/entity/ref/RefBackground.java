package application.entity.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;

@Entity
@Table(name = "ref_Background")
@NamedQueries({ @NamedQuery(name = "RefBackground.findAll", query = " SELECT T FROM RefBackground T"),
	@NamedQuery(name = "RefBackground.byName", query = " SELECT T FROM RefBackground T WHERE t.name = :name"), })
public final class RefBackground implements HandleItemInf {

	@Transient
	public static String queryByAll = "RefBackground.findAll";
	@Transient
	public static String queryByName = "RefBackground.byName";

	/**
	 * Make this class unable to be created via code.
	 */
	private RefBackground() {
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
