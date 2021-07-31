package application.entity.ref.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.data.HandleItemInf;
import application.data.StaticData;
import application.entity.ref.RefAncestry;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "ref_AncestryData")
@NamedQueries({
	@NamedQuery(name = RefAncestryData.QUERY_BY_ALL, query = " SELECT T FROM RefAncestryData T"),
	@NamedQuery(name = RefAncestryData.QUERY_BY_ANCESTRY_NAME, query = "SELECT T FROM RefAncestryData T where T.name = :name ") })
public class RefAncestryData extends StaticData implements HandleItemInf, Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String QUERY_BY_ALL = "RefAncestryData.findAll";
	@Transient
	public static final String QUERY_BY_ANCESTRY_NAME = "RefAncestryData.findByAncestryName";


	@Id
	@Column(name = "Ancestry_Name", length = 50)
	private String name;

	@OneToOne()

	@JoinColumn(name = "Ancestry_Name")
	@JsonBackReference
	private RefAncestry ancestry;

	@Lob
	@Column(name = "InitialDescription")
	private String initialDescription;

	@Lob
	@Column(name = "PhysicalDescription")
	private String physicalDescription;

	@Lob
	@Column(name = "Society")
	private String society;

	@Lob
	@Column(name = "AlignmentAndReligion")
	private String alignmentAndReligion;

	@Lob
	@Column(name = "SampleNames")
	private String sampleNames;

	@Override
	public String methodGetKey() {
		// TODO Auto-generated method stub
		return "getName";
	}

	public void copyFields(final RefAncestryData item) {
		this.name = item.name;
		this.ancestry = item.ancestry;
		this.alignmentAndReligion = item.alignmentAndReligion;
		this.initialDescription = item.initialDescription;
		this.physicalDescription = item.physicalDescription;
		this.society = item.society;
		this.sampleNames = item.sampleNames;
	}

	@Override
	public HandleItemInf updateItem(final HandleItemInf pNewItem) {
		RefAncestryData newItem = (RefAncestryData) pNewItem;
		this.copyFields(newItem);
		return this;
	}
}
