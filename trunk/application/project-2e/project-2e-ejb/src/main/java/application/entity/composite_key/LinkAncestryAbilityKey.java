package application.entity.composite_key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import application.entity.ref.RefAbility;
import application.entity.ref.RefAncestry;

@Embeddable
public class LinkAncestryAbilityKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "Ancestry", length = 50)
	private String ancestry;

	@Column(name = "Ability")
	private char ability;

	public LinkAncestryAbilityKey() {
		// TODO Auto-generated constructor stub
	}

	public String getAncestry() {
		return this.ancestry;
	}

	public void setAncestry(final String pAncestry) {
		this.ancestry = pAncestry;
	}

	public char getAbility() {
		return this.ability;
	}

	public void setAbility(final char pAbility) {
		this.ability = pAbility;
	}


}
