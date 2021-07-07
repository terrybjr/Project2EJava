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
	@Column(name = "character_id")
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "character_id")
	private Character character;

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

	public Character getCharacter() {
		return this.character;
	}

	public void setCharacter(final Character pCharacter) {
		this.character = pCharacter;
	}

	public void copyFields(final Inventory item) {
		this.id = item.id;
		this.character = item.character;
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
