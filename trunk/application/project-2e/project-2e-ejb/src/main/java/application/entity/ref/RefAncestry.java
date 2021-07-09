package application.entity.ref;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;
import application.entity.Character;
import application.entity.ref.links.LinkAncestryAbilityBoost;
import application.entity.ref.links.LinkAncestryAbilityFlaw;

@Entity
@Table(name = "ref_Ancestry")
@NamedQueries ({
	@NamedQuery(name = "RefAncestry.findAll", query = " SELECT T FROM RefAncestry T"),
	@NamedQuery(name = "RefAncestry.byName", query = " SELECT T FROM RefAncestry T WHERE t.name = :name"),
})
public final class RefAncestry implements HandleItemInf {
	@Transient
	public static String queryByAll = "RefAncestry.findAll";
	@Transient
	public static String queryByName = "RefAncestry.byName";

	/**
	 * Make this class unable to be created via code.
	 */
	private RefAncestry() {
		super();
	}

	@Id
	@Column(name = "Name", length = 50)
	private String name;
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
	//	// TODO: should be a linking table between, languages
	//	@Column(name = "List_Languages")
	//	ArrayList<String> languagesList;
	//	// TODO: Not correct.. need update
	//	@Column(name = "Traits", length = 50)
	//	private String Traits;
	//	@Column(name = "List_Heritages")
	//	ArrayList<String> heritagesList;
	//	// TODO: should be a linking table between, ancestrytFeats
	//	@Column(name = "List_Ancestry_Feats")
	//	ArrayList<String> ancestryFeatsList;

	public String getName() {
		return this.name;
	}

	public void setName(final String pName) {
		this.name = pName;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(final String pSize) {
		this.size = pSize;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(final int pSpeed) {
		this.speed = pSpeed;
	}

	public List<LinkAncestryAbilityBoost> getAbilityBoostsList() {
		return this.abilityBoostsList;
	}

	public void setAbilityBoostsList(final List<LinkAncestryAbilityBoost> pAbilityBoostsList) {
		this.abilityBoostsList = pAbilityBoostsList;
	}

	public List<LinkAncestryAbilityFlaw> getAbilityFlawsList() {
		return this.abilityFlawsList;
	}

	public void setAbilityFlawsList(final List<LinkAncestryAbilityFlaw> pAbilityFlawsList) {
		this.abilityFlawsList = pAbilityFlawsList;
	}

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

}
