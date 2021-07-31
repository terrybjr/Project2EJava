package application.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import application.data.HandleItemInf;
import application.entity.ref.RefAlignment;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefBackground;
import application.entity.ref.RefCreatureType;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "user")
@Table(name = "`character`")
@NamedQueries ({
	@NamedQuery(name = Character.QUERY_BY_ALL, query = " SELECT T FROM Character T"),
	@NamedQuery(name = Character.QUERY_BY_ID, query = " SELECT T FROM Character T WHERE t.id = :id"),
})
public class Character implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "Character.findAll";
	@Transient
	public static final String QUERY_BY_ID = "Character.byId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	@JsonBackReference // stops the JSON from looping to infinity
	private User user;

	@OneToOne(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Inventory inventory;

	@OneToOne(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Background tempbackground;

	@OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Level> levels;

	@Column(name = "CharacterName")
	private String characterName;

	@ManyToOne
	@JoinColumn(name = "Ancestry", nullable = true)
	private RefAncestry ancestry;
	@ManyToOne
	@JoinColumn(name = "Background", nullable = true)
	private RefBackground background;
	@ManyToOne
	@JoinColumn(name = "CreatureType", nullable = true)
	private RefCreatureType creatureType;
	@ManyToOne
	@JoinColumn(name = "Alignment", nullable = true)
	private RefAlignment alignment;

	public Character() {
		super();
	}

	public Character(final String pCharacterName, final User pUser, final Inventory pInventory,
			final RefBackground pBackground, final ArrayList<Level> pArrayList) {
		super();
		this.setUser(pUser);
		this.setInventory(pInventory);
		this.inventory.setCharacter(this);
		this.setLevels(pArrayList);
		this.setCharacterName(pCharacterName);
		this.setBackground(pBackground);
	}



	public void copyFields(final Character item) {
		this.id = item.id;
		this.characterName = item.characterName;
		this.ancestry = item.ancestry;
		this.background = item.background;
		this.inventory = item.inventory;
		this.levels = item.levels;
		this.user = item.user;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		Character newItem = (Character) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
