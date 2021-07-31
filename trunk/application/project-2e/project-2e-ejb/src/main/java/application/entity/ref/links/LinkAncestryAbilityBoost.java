package application.entity.ref.links;

import java.io.Serializable;

import javax.persistence.Column;
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
import application.entity.composite_key.LinkAncestryAbilityKey;
import application.entity.ref.RefAbility;
import application.entity.ref.RefAncestry;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "ancestry")
@Table(name = "Ancestry_Ability_Boost")
@NamedQueries({
	@NamedQuery(name = LinkAncestryAbilityBoost.QUERY_BY_ALL, query = " SELECT T FROM LinkAncestryAbilityBoost T"), })
public class LinkAncestryAbilityBoost implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "LinkAncestryAbilityBoost.findAll";

	@EmbeddedId
	LinkAncestryAbilityKey key;

	@ManyToOne
	@MapsId("ancestry")
	@JoinColumn(name = "Ancestry")
	@JsonBackReference
	RefAncestry ancestry;

	@ManyToOne
	@MapsId("ability")
	@JoinColumn(name = "Ability")
	RefAbility ability;

	@Column(name = "Quantity")
	private int quantity;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getKey";
	}

	public void copyFields(final LinkAncestryAbilityBoost item) {
		this.ability = item.ability;
		this.ancestry = item.ancestry;
		this.quantity = item.quantity;
		this.key = item.key;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		LinkAncestryAbilityBoost newItem = (LinkAncestryAbilityBoost) pNewItem;
		this.copyFields(newItem);
		return this;
	}
}
