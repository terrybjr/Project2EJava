package application.data.sheet;

import java.util.List;

import application.utils.MiscUtils;

public class SheetPerception {
	private int perception;
	private int wisBonus;
	private char proficiency;
	private int itemBonus;
	private List<String> senses;

	public int getPerception() {
		return this.perception;
	}

	public void setPerception(final int pPerception) {
		this.perception = pPerception;
	}

	public int getWisBonus() {
		return this.wisBonus;
	}

	public void setWisBonus(final int pWisBonus) {
		this.wisBonus = pWisBonus;
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

	public List<String> getSenses() {
		return this.senses;
	}

	public void setSenses(final List<String> pSenses) {
		this.senses = pSenses;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
