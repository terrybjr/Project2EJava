package application.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne(mappedBy = "playerCharacter", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Inventory inventory;

	@OneToOne(mappedBy = "playerCharacter", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Background background;

	@OneToMany(mappedBy = "playerCharacter")
	private List<Level> levels;



	public PlayerCharacter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayerCharacter(final Long pId, final String pName, final User pUser, final Inventory pInventory, final Background pBackground,
			final List<Level> pLevels) {
		super();
		this.id = pId;
		this.name = pName;
		this.user = pUser;
		this.inventory = pInventory;
		this.background = pBackground;
		this.levels = pLevels;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(final User pUser) {
		this.user = pUser;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public void setInventory(final Inventory pInventory) {
		this.inventory = pInventory;
	}

	public Background getBackground() {
		return this.background;
	}

	public void setBackground(final Background pBackground) {
		this.background = pBackground;
	}

	public List<Level> getLevels() {
		return this.levels;
	}

	public void setLevels(final List<Level> pLevels) {
		this.levels = pLevels;
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
