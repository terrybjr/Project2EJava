package application.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import application.data.HandleItemInf;

@Entity
public class Background implements HandleItemInf, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@OneToOne
	@JoinColumn(name = "player_character_id")
	@JsonbTransient
	private PlayerCharacter playerCharacter;

	public Background() {
		super();
	}

	public Background(final Long pId, final PlayerCharacter pPlayerCharacter) {
		super();
		this.id = pId;
		this.playerCharacter = pPlayerCharacter;
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

	public void copyFields(final Background item) {
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
		Background newItem = (Background) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
