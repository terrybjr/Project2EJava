package application.data.sheet;

import application.utils.MiscUtils;

/**
 * Represents a characters ability scores. Modifiers are expected to be a
 * calculated value. Adjustments, are also to be calculated, but during the
 * creation of this object.
 * 
 * @author brianterry
 *
 */
public class SheetAbilityScores {
	int strength;
	// TODO I think these will need to be replaced by some sort of tuple, with a
	// "total adjust" and a list fo reasons why it's being adjusted, and then the
	// type of adjustment... to make sure they dont stack... otherwise, it should
	// all be handled algorithmically... and we don't care about transparency.
	private int strengthAdjustment;
	private int dexterity;
	private int dexterityAdjustment;
	private int constitution;
	private int constitutionAdjustment;
	private int intelligence;
	private int intelligenceAdjustment;
	private int wisdom;
	private int wisdomAdjustment;
	private int charisma;
	private int charismaAdjustment;

	public int getStrength() {
		return this.strength;
	}

	public void setStrength(final int pStrength) {
		this.strength = pStrength;
	}

	public int getStrengthAdjustment() {
		return this.strengthAdjustment;
	}

	public void setStrengthAdjustment(final int pStrengthAdjustment) {
		this.strengthAdjustment = pStrengthAdjustment;
	}

	public int getDexterity() {
		return this.dexterity;
	}

	public void setDexterity(final int pDexterity) {
		this.dexterity = pDexterity;
	}

	public int getDexterityAdjustment() {
		return this.dexterityAdjustment;
	}

	public void setDexterityAdjustment(final int pDexterityAdjustment) {
		this.dexterityAdjustment = pDexterityAdjustment;
	}

	public int getConstitution() {
		return this.constitution;
	}

	public void setConstitution(final int pConstitution) {
		this.constitution = pConstitution;
	}

	public int getConstitutionAdjustment() {
		return this.constitutionAdjustment;
	}

	public void setConstitutionAdjustment(final int pConstitutionAdjustment) {
		this.constitutionAdjustment = pConstitutionAdjustment;
	}

	public int getIntelligence() {
		return this.intelligence;
	}

	public void setIntelligence(final int pIntelligence) {
		this.intelligence = pIntelligence;
	}

	public int getIntelligenceAdjustment() {
		return this.intelligenceAdjustment;
	}

	public void setIntelligenceAdjustment(final int pIntelligenceAdjustment) {
		this.intelligenceAdjustment = pIntelligenceAdjustment;
	}

	public int getWisdom() {
		return this.wisdom;
	}

	public void setWisdom(final int pWisdom) {
		this.wisdom = pWisdom;
	}

	public int getWisdomAdjustment() {
		return this.wisdomAdjustment;
	}

	public void setWisdomAdjustment(final int pWisdomAdjustment) {
		this.wisdomAdjustment = pWisdomAdjustment;
	}

	public int getCharisma() {
		return this.charisma;
	}

	public void setCharisma(final int pCharisma) {
		this.charisma = pCharisma;
	}

	public int getCharismaAdjustment() {
		return this.charismaAdjustment;
	}

	public void setCharismaAdjustment(final int pCharismaAdjustment) {
		this.charismaAdjustment = pCharismaAdjustment;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
