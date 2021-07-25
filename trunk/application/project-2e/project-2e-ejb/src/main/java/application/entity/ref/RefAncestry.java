package application.entity.ref;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;
import application.data.StaticData;
import application.entity.ref.links.LinkAncestryAbilityBoost;
import application.entity.ref.links.LinkAncestryAbilityFlaw;
import application.entity.ref.links.LinkAncestryLanguage;
import application.utils.MiscUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "ref_Ancestry")
@NamedQueries ({
	@NamedQuery(name = "RefAncestry.findAll", query = " SELECT DISTINCT T FROM RefAncestry T"),
	@NamedQuery(name = "RefAncestry.findAllAbilityBoosts", query = " SELECT DISTINCT T FROM RefAncestry T LEFT JOIN FETCH T.abilityBoostsList"),
	@NamedQuery(name = "RefAncestry.findAllAbilityFlaws", query = " SELECT DISTINCT T FROM RefAncestry T LEFT JOIN FETCH T.abilityFlawsList"),
	@NamedQuery(name = "RefAncestry.findAllLanguages", query = " SELECT DISTINCT T FROM RefAncestry T LEFT JOIN FETCH T.languagesList"),
	@NamedQuery(name = "RefAncestry.byName", query = " SELECT T FROM RefAncestry T WHERE t.name = :name"),
})
public class RefAncestry extends StaticData implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static String queryByAll = "RefAncestry.findAll";
	@Transient
	public static String queryByName = "RefAncestry.byName";
	@Transient
	public static String queryByAllAbilityBoost = "RefAncestry.findAllAbilityBoosts";
	@Transient
	public static String queryByAllAbilityFlaw = "RefAncestry.findAllAbilityFlaws";
	@Transient
	public static String queryByAllLanguages = "RefAncestry.findAllLanguages";

	@Id
	@Column(name = "Name", length = 50)
	private String name;
	@Column(name = "Hitpoints")
	private int hitpoints;
	// TODO: Size should also be a ref table? How deal with that?
	@Column(name = "Size", nullable = false)
	private String size;
	@Column(name = "Speed")
	private int speed;
	// TODO: should be a linking table between, abilities
	@OneToMany(mappedBy = "ancestry")
	private List<LinkAncestryAbilityBoost> abilityBoostsList;
	// TODO: should be a linking table between, abilities
	@OneToMany(mappedBy = "ancestry")
	private List<LinkAncestryAbilityFlaw> abilityFlawsList;
	@OneToMany(mappedBy = "ancestry")
	private List<LinkAncestryLanguage> languagesList;

	// uncomment to make data part of the DS
	//	@OneToOne(mappedBy = "ancestry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//	@PrimaryKeyJoinColumn
	//	private RefAncestryData ancestryData;

	//	ArrayList<String> languagesList;
	//	// TODO: Not correct.. need update
	//	@Column(name = "Traits", length = 50)
	//	private String Traits;
	//	@Column(name = "List_Heritages")
	//	ArrayList<String> heritagesList;
	//	// TODO: should be a linking table between, ancestrytFeats
	//	@Column(name = "List_Ancestry_Feats")
	//	ArrayList<String> ancestryFeatsList;
	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefAncestry item) {
		this.setName(item.getName());
		this.setSize(item.getSize());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefAncestry newItem = (RefAncestry) pNewItem;
		this.copyFields(newItem);
		return this;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
