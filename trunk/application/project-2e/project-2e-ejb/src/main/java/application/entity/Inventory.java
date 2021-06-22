package application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import application.data.HandleItemInf;

@Entity
public class Inventory implements HandleItemInf {

	@Id
	@Column(name = "player_character_id")
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "player_character_id")
	private PlayerCharacter playerCharacter;

	public Inventory() {
		super();
	}

	public Inventory(final Long pId) {
		super();
		this.id = pId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long pId) {
		this.id = pId;
	}

	public PlayerCharacter getPlayerCharacter() {
		return this.playerCharacter;
	}

	public void setPlayerCharacter(final PlayerCharacter pPlayerCharacter) {
		this.playerCharacter = pPlayerCharacter;
	}

	public void copyFields(final Inventory item) {
		this.id = item.id;
		this.playerCharacter = item.playerCharacter;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		Inventory newItem = (Inventory) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
