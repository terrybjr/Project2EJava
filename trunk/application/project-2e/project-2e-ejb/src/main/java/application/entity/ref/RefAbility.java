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
@Table(name = "ref_Ability")
@NamedQueries({ @NamedQuery(name = "RefAbility.findAll", query = " SELECT T FROM RefAbility T"),
	@NamedQuery(name = "RefAbility.byCode", query = " SELECT T FROM RefAbility T WHERE t.code = :code"), })
public class RefAbility implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static String queryByAll = "RefAbility.findAll";
	@Transient
	public static String queryByName = "RefAbility.byCode";

	@Id
	@Column(name = "Code")
	private char code;

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
