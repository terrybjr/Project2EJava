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
@Table(name = "ref_Size")
@NamedQueries({ @NamedQuery(name = "RefSize.findAll", query = " SELECT T FROM RefSize T"),
	@NamedQuery(name = "RefSize.byCode", query = " SELECT T FROM RefSize T WHERE t.code = :code"), })
public class RefSize implements HandleItemInf {

	@Transient
	public static String queryByAll = "RefSize.findAll";
	@Transient
	public static String queryByName = "RefSize.byCode";

	/**
	 * Make this class unable to be created via code.
	 */
	private RefSize() {
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

	public void copyFields(final RefSize item) {
		this.setCode(item.getCode());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefSize newItem = (RefSize) pNewItem;
		this.copyFields(newItem);
		return this;
	}


}
