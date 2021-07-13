package application.data.sheet;

import application.utils.MiscUtils;

public class SheetSavingThrows {
	private int fortitudeConBonus;
	private int fortitudeItemBonus;
	private int fortitudeProfBonus;
	private char fortitudeProficiency;
	private int reflexDexBonus;
	private int reflexItemBonus;
	private int reflexProfBonus;
	private char reflexProficiency;
	private int willWisBonus;
	private int willItemBonus;
	private int willProfBonus;
	private char willProficiency;

	public int getFortitudeConBonus() {
		return this.fortitudeConBonus;
	}

	public void setFortitudeConBonus(final int pFortitudeConBonus) {
		this.fortitudeConBonus = pFortitudeConBonus;
	}

	public int getFortitudeItemBonus() {
		return this.fortitudeItemBonus;
	}

	public void setFortitudeItemBonus(final int pFortitudeItemBonus) {
		this.fortitudeItemBonus = pFortitudeItemBonus;
	}

	public int getFortitudeProfBonus() {
		return this.fortitudeProfBonus;
	}

	public void setFortitudeProfBonus(final int pFortitudeProfBonus) {
		this.fortitudeProfBonus = pFortitudeProfBonus;
	}

	public char getFortitudeProficiency() {
		return this.fortitudeProficiency;
	}

	public void setFortitudeProficiency(final char pFortitudeProficiency) {
		this.fortitudeProficiency = pFortitudeProficiency;
	}

	public int getReflexDexBonus() {
		return this.reflexDexBonus;
	}

	public void setReflexDexBonus(final int pReflexDexBonus) {
		this.reflexDexBonus = pReflexDexBonus;
	}

	public int getReflexItemBonus() {
		return this.reflexItemBonus;
	}

	public void setReflexItemBonus(final int pReflexItemBonus) {
		this.reflexItemBonus = pReflexItemBonus;
	}

	public int getReflexProfBonus() {
		return this.reflexProfBonus;
	}

	public void setReflexProfBonus(final int pReflexProfBonus) {
		this.reflexProfBonus = pReflexProfBonus;
	}

	public char getReflexProficiency() {
		return this.reflexProficiency;
	}

	public void setReflexProficiency(final char pReflexProficiency) {
		this.reflexProficiency = pReflexProficiency;
	}

	public int getWillWisBonus() {
		return this.willWisBonus;
	}

	public void setWillWisBonus(final int pWillWisBonus) {
		this.willWisBonus = pWillWisBonus;
	}

	public int getWillItemBonus() {
		return this.willItemBonus;
	}

	public void setWillItemBonus(final int pWillItemBonus) {
		this.willItemBonus = pWillItemBonus;
	}

	public int getWillProfBonus() {
		return this.willProfBonus;
	}

	public void setWillProfBonus(final int pWillProfBonus) {
		this.willProfBonus = pWillProfBonus;
	}

	public char getWillProficiency() {
		return this.willProficiency;
	}

	public void setWillProficiency(final char pWillProficiency) {
		this.willProficiency = pWillProficiency;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
