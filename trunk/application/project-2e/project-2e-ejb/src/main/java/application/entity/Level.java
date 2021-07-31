package application.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.data.HandleItemInf;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "character")
public class Level implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ClassName")
	String className;

	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	@JsonBackReference
	private Character character;

	public void copyFields(final Level item) {
		this.id = item.id;
		this.character = item.character;
		this.className = item.className;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		Level newItem = (Level) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}

