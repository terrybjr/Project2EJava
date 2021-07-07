package application.entity;



import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.data.HandleItemInf;

@Entity
public class Background implements HandleItemInf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@OneToOne
	@JoinColumn(name = "character_id")
	@JsonBackReference // stops the JSON from looping to infinity
	private Character character;

	public Background() {
		super();
	}

	public Background(final Long pId, final Character pCharacter) {
		super();
		this.id = pId;
		this.character = pCharacter;
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

	public void copyFields(final Background item) {
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
		Background newItem = (Background) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
