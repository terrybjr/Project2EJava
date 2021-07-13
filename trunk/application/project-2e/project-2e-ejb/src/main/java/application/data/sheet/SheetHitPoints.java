package application.data.sheet;

import java.util.List;

import application.utils.MiscUtils;

public class SheetHitPoints {
	private int current;
	private int temporary;
	private int dying;
	private int wounded;
	private List<String> resistances;
	private List<String> immunities;
	private List<String> conditions;

	public int getCurrent() {
		return this.current;
	}

	public void setCurrent(final int pCurrent) {
		this.current = pCurrent;
	}

	public int getTemporary() {
		return this.temporary;
	}

	public void setTemporary(final int pTemporary) {
		this.temporary = pTemporary;
	}

	public int getDying() {
		return this.dying;
	}

	public void setDying(final int pDying) {
		this.dying = pDying;
	}

	public int getWounded() {
		return this.wounded;
	}

	public void setWounded(final int pWounded) {
		this.wounded = pWounded;
	}

	public List<String> getResistances() {
		return this.resistances;
	}

	public void setResistances(final List<String> pResistances) {
		this.resistances = pResistances;
	}

	public List<String> getImmunities() {
		return this.immunities;
	}

	public void setImmunities(final List<String> pImmunities) {
		this.immunities = pImmunities;
	}

	public List<String> getConditions() {
		return this.conditions;
	}

	public void setConditions(final List<String> pConditions) {
		this.conditions = pConditions;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
