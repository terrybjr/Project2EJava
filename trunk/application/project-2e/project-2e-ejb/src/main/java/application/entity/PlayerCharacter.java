package application.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import application.data.HandleItemInf;

@Entity
@Table(name = "player_character")
@NamedQueries ({
	@NamedQuery(name = "PlayerCharacter.findAll", query = " SELECT T FROM PlayerCharacter T"),
	@NamedQuery(name = "PlayerCharacter.byId", query = " SELECT T FROM PlayerCharacter T WHERE t.id = :id"),
})
public class PlayerCharacter implements HandleItemInf {
	@Transient
	public static String queryByAll = "PlayerCharacter.findAll";
	@Transient
	public static String queryById = "PlayerCharacter.byId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	String name;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	User user;


	public PlayerCharacter() {
		super();
	}

	public PlayerCharacter(final Long pId, final String pName) {
		super();
		this.id = pId;
		this.name = pName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long pId) {
		this.id = pId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String pName) {
		this.name = pName;
	}

	public void copyFields(final PlayerCharacter item) {
		this.id = item.id;
		this.setName(item.getName());
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		PlayerCharacter newItem = (PlayerCharacter) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
