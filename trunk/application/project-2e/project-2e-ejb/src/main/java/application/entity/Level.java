package application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import application.data.HandleItemInf;

@Entity
public class Level implements HandleItemInf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	private Character character;

	public Level() {
		super();
	}

	public Level(final Long pId) {
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

	public void copyFields(final Level item) {
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
		Level newItem = (Level) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}

