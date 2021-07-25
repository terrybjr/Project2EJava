package application.entity.ref.links;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
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
import application.entity.ref.RefAbility;
import application.entity.ref.RefAncestry;
import application.utils.MiscUtils;
import lombok.Data;

@Entity
@Data
@Table(name = "Ancestry_Ability_Flaw")
@NamedQueries({
	@NamedQuery(name = "LinkAncestryAbilityFlaw.findAll", query = " SELECT T FROM LinkAncestryAbilityFlaw T"), })
public class LinkAncestryAbilityFlaw implements HandleItemInf {
	@Transient
	public static String queryByAll = "LinkAncestryAbilityBoost.findAll";

	@EmbeddedId
	@JsonIgnore
	LinkAncestryAbilityKey key;

	@ManyToOne
	@MapsId("ancestry")
	@JoinColumn(name = "Ancestry")
	@JsonBackReference
	private RefAncestry ancestry;

	@ManyToOne
	@MapsId("ability")
	@JoinColumn(name = "Ability")
	private RefAbility ability;

	@Column(name = "Quantity")
	private int quantity;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getKey";
	}

	public void copyFields(final LinkAncestryAbilityFlaw item) {
		this.setAbility(item.getAbility());
		this.setAncestry(item.getAncestry());
		this.setQuantity(item.getQuantity());
		this.setKey(item.getKey());
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkAncestryAbilityFlaw newItem = (LinkAncestryAbilityFlaw) pNewItem;
		this.copyFields(newItem);
		return this;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
