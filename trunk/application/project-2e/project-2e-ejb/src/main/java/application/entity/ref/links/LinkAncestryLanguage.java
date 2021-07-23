package application.entity.ref.links;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import application.data.HandleItemInf;
import application.entity.composite_key.LinkAncestryAbilityKey;
import application.entity.composite_key.LinkAncestryLanguageKey;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefLanguage;
import application.utils.MiscUtils;
import lombok.Data;

@Entity
@Data
@Table(name = "Ancestry_Language")
@NamedQueries({
	@NamedQuery(name = "LinkAncestryLanguage.findAll", query = " SELECT T FROM LinkAncestryLanguage T"), })
public class LinkAncestryLanguage implements HandleItemInf {
	@Transient
	public static String queryByAll = "LinkAncestryLanguage.findAll";

	@EmbeddedId
	@JsonIgnore
	private LinkAncestryLanguageKey key;

	@ManyToOne
	@MapsId("name")
	@JoinColumn(name = "Ancestry")
	@JsonBackReference
	private RefAncestry ancestry;

	@ManyToOne
	@MapsId("name")
	@JoinColumn(name = "language")
	private RefLanguage language;

	public LinkAncestryLanguageKey getKey() {
		return this.key;
	}

	/**
	 * Make this class unable to be created via code.
	 */
	private LinkAncestryLanguage() {
		super();
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getKey";
	}

	public void copyFields(final LinkAncestryLanguage item) {
		this.setAncestry(item.getAncestry());
		this.setKey(this.getKey());
		this.setLanguage(this.getLanguage());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkAncestryLanguage newItem = (LinkAncestryLanguage) pNewItem;
		this.copyFields(newItem);
		return this;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
