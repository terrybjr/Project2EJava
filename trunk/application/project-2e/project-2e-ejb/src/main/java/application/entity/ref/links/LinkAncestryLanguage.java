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
import application.entity.composite_key.LinkAncestryLanguageKey;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefLanguage;
import application.utils.MiscUtils;

@Entity
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

	public void setKey(final LinkAncestryLanguageKey pKey) {
		this.key = pKey;
	}

	public RefAncestry getAncestry() {
		return this.ancestry;
	}

	public void setAncestry(final RefAncestry pAncestry) {
		this.ancestry = pAncestry;
	}

	public RefLanguage getLanguage() {
		return this.language;
	}

	public void setLanguage(final RefLanguage pLanguage) {
		this.language = pLanguage;
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
