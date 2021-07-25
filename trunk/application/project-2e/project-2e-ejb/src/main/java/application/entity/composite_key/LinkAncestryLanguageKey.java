package application.entity.composite_key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class LinkAncestryLanguageKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Ancestry", length = 50)
	private String ancestry;

	@Column(name = "Language", length = 50)
	private String language;

}
