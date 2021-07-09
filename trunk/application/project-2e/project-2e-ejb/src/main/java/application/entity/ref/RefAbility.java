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
@Table(name = "ref_Ability")
@NamedQueries({ @NamedQuery(name = "RefAbility.findAll", query = " SELECT T FROM RefAbility T"),
	@NamedQuery(name = "RefAbility.byCode", query = " SELECT T FROM RefAbility T WHERE t.code = :code"), })
public class RefAbility implements HandleItemInf {

	@Transient
	public static String queryByAll = "RefAbility.findAll";
	@Transient
	public static String queryByName = "RefAbility.byCode";

	/**
	 * Make this class unable to be created via code.
	 */
	private RefAbility() {
		super();
	}

	@Id
	@Column(name = "Code")
	private char code;

	public char getCode() {
		return this.code;
	}

	public void setCode(final char pCode) {
		this.code = pCode;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getCode";
	}

	public void copyFields(final RefAbility item) {
		this.setCode(item.getCode());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefAbility newItem = (RefAbility) pNewItem;
		this.copyFields(newItem);
		return this;
	}


}
