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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import application.data.HandleItemInf;
import application.entity.ref.RefAlignment;
import application.entity.ref.RefAncestry;
import application.entity.ref.RefBackground;
import application.entity.ref.RefCreatureType;
import lombok.Data;

@Entity
@Data
@Table(name = "`character`")
@NamedQueries ({
	@NamedQuery(name = "Character.findAll", query = " SELECT T FROM Character T"),
	@NamedQuery(name = "Character.byId", query = " SELECT T FROM Character T WHERE t.id = :id"),
})
public class Character implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static String queryByAll = "Character.findAll";
	@Transient
	public static String queryById = "Character.byId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	@JsonBackReference // stops the JSON from looping to infinity
	private User user;

	@OneToOne(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Inventory inventory;

	@OneToOne(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
		this.user = pUser;
		this.inventory = pInventory;
		this.levels = pArrayList;
		this.characterName = pCharacterName;
		this.background = pBackground;
	}



	public void copyFields(final Character item) {
		this.setId(item.getId());
		this.setCharacterName(item.getCharacterName());
		this.setAncestry(item.getAncestry());
		this.setBackground(item.getBackground());
		this.setInventory(item.getInventory());
		this.setLevels(item.getLevels());
		this.setUser(this.getUser());
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
