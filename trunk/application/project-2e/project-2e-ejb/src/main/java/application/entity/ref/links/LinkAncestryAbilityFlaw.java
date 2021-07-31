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
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "ancestry")
@Table(name = "Ancestry_Ability_Flaw")
@NamedQueries({
	@NamedQuery(name = LinkAncestryAbilityFlaw.QUERY_BY_ALL, query = " SELECT T FROM LinkAncestryAbilityFlaw T"), })
public class LinkAncestryAbilityFlaw implements HandleItemInf {
	@Transient
	public static final String QUERY_BY_ALL = "LinkAncestryAbilityFlaw.findAll";

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
		this.ability = item.ability;
		this.ancestry = item.ancestry;
		this.quantity = item.quantity;
		this.key = item.key;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkAncestryAbilityFlaw newItem = (LinkAncestryAbilityFlaw) pNewItem;
		this.copyFields(newItem);
		return this;
	}
}
