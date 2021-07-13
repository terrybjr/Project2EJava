package application.data.sheet;

import application.utils.MiscUtils;

public class SheetClassDC {
	private int classDC;
	private int key;
	private int proficiencyBonus;
	private char proficiency;
	private int itemBonus;

	public int getClassDC() {
		return this.classDC;
	}

	public void setClassDC(final int pClassDC) {
		this.classDC = pClassDC;
	}

	public int getKey() {
		return this.key;
	}

	public void setKey(final int pKey) {
		this.key = pKey;
	}

	public int getProficiencyBonus() {
		return this.proficiencyBonus;
	}

	public void setProficiencyBonus(final int pProficiencyBonus) {
		this.proficiencyBonus = pProficiencyBonus;
	}

	public char getProficiency() {
		return this.proficiency;
	}

	public void setProficiency(final char pProficiency) {
		this.proficiency = pProficiency;
	}

	public int getItemBonus() {
		return this.itemBonus;
	}

	public void setItemBonus(final int pItemBonus) {
		this.itemBonus = pItemBonus;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
