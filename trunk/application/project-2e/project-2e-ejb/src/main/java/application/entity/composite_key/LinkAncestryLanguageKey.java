package application.entity.composite_key;

import java.io.Serializable;

import javax.persistence.Column;

import application.utils.MiscUtils;

public class LinkAncestryLanguageKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "Ancestry", length = 50)
	private String ancestry;

	@Column(name = "Language", length = 50)
	private String ability;

	public String getAncestry() {
		return this.ancestry;
	}

	public void setAncestry(final String pAncestry) {
		this.ancestry = pAncestry;
	}

	public String getAbility() {
		return this.ability;
	}

	public void setAbility(final String pAbility) {
		this.ability = pAbility;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
