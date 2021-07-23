package application.entity.composite_key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import application.entity.ref.RefAbility;
import application.entity.ref.RefAncestry;
import application.entity.ref.data.RefAncestryData;
import application.utils.MiscUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
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
	}

}
