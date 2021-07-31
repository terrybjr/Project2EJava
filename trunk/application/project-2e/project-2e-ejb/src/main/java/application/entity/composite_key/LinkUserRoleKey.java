package application.entity.composite_key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import application.security.Authority;
import lombok.Data;

@Embeddable
@Data
public class LinkUserRoleKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "UserId", length = 50)
	private long userId;

	@Column(name = "Role", length = 50)
	@Enumerated(EnumType.STRING)
	private Authority role;

}
