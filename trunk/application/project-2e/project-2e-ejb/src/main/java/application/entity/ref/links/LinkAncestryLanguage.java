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
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "ancestry")
@Table(name = "Ancestry_Language")
@NamedQueries({
	@NamedQuery(name = LinkAncestryLanguage.QUERY_BY_ALL, query = " SELECT T FROM LinkAncestryLanguage T"), })
public class LinkAncestryLanguage implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "LinkAncestryLanguage.findAll";

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

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getKey";
	}

	public void copyFields(final LinkAncestryLanguage item) {
		this.key = item.key;
		this.ancestry = item.ancestry;
		this.language = item.language;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkAncestryLanguage newItem = (LinkAncestryLanguage) pNewItem;
		this.copyFields(newItem);
		return this;
	}
}
