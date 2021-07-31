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
import application.data.StaticData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "ref_Size")
@NamedQueries({ @NamedQuery(name = RefSize.QUERY_BY_ALL, query = " SELECT T FROM RefSize T"),
	@NamedQuery(name = RefSize.QUERY_BY_NAME, query = " SELECT T FROM RefSize T WHERE t.code = :code"), })
public class RefSize extends StaticData implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String QUERY_BY_ALL = "RefSize.findAll";
	@Transient
	public static final String QUERY_BY_NAME = "RefSize.byCode";

	@Id
	@Column(name = "Code")
	private char code;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getCode";
	}

	public void copyFields(final RefSize item) {
		this.code = item.code;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefSize newItem = (RefSize) pNewItem;
		this.copyFields(newItem);
		return this;
	}


}
