package application.data.sheet;

import application.utils.MiscUtils;
import lombok.Data;

/**
 * Represents a characters ability scores. Modifiers are expected to be a
 * calculated value. Adjustments, are also to be calculated, but during the
 * creation of this object.
 * 
 * @author brianterry
 *
 */
@Data
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
}
