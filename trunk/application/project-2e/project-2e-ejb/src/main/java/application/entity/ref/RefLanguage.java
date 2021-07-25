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
import application.utils.MiscUtils;
import lombok.Data;

@Entity
@Data
@Table(name = "ref_Language")
@NamedQueries({ @NamedQuery(name = "RefLanguage.findAll", query = " SELECT T FROM RefLanguage T"),
	@NamedQuery(name = "RefLanguage.byName", query = " SELECT T FROM RefLanguage T WHERE t.name = :name"), })
public class RefLanguage extends StaticData implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static String queryByAll = "RefLanguage.findAll";
	@Transient
	public static String queryByName = "RefLanguage.byName";


	@Id
	@Column(name = "Name", length = 50)
	private String name;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefLanguage item) {
		this.setName(item.getName());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefLanguage newItem = (RefLanguage) pNewItem;
		this.copyFields(newItem);
		return this;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
