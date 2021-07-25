package application.entity.ref.links;

import java.io.Serializable;

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
import application.data.HandleItemInf;
import application.entity.composite_key.LinkAncestryLanguageKey;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefLanguage;
import lombok.Data;

@Entity
@Data
@Table(name = "Ancestry_Language")
@NamedQueries({
	@NamedQuery(name = "LinkAncestryLanguage.findAll", query = " SELECT T FROM LinkAncestryLanguage T"), })
public class LinkAncestryLanguage implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static String queryByAll = "LinkAncestryLanguage.findAll";

	@EmbeddedId
	private LinkAncestryLanguageKey key;

	@ManyToOne
	@MapsId("ancestry")
	@JoinColumn(name = "Ancestry")
	@JsonBackReference
	private RefAncestry ancestry;

	@ManyToOne
	@MapsId("language")
	@JoinColumn(name = "Language")
	private RefLanguage language;

	public LinkAncestryLanguageKey getKey() {
		return this.key;
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
}
