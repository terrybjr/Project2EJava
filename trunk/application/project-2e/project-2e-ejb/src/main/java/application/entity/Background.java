package application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import application.data.HandleItemInf;

@Entity
public class Background implements HandleItemInf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "player_character_id")
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
