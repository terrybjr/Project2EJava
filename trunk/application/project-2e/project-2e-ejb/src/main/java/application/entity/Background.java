package application.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.data.HandleItemInf;
import lombok.Data;

@Entity
@Data
public class Background implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@OneToOne
	@JoinColumn(name = "character_id")
	@JsonBackReference // stops the JSON from looping to infinity
	private Character character;

	public void copyFields(final Background item) {
		this.id = item.id;
		this.character = item.character;
	}

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getId";
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		Background newItem = (Background) pNewItem;
		this.copyFields(newItem);
		return this;
	}

}
