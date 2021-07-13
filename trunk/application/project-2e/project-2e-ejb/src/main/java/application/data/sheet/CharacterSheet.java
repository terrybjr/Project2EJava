package application.data.sheet;

import application.utils.MiscUtils;

public class CharacterSheet {

	private SheetDemographics characterDemographics;
	private SheetAbilityScores sheetAbilityScores;
	private SheetSavingThrows sheetSavingThrows;
	private SheetHitPoints sheetHitpoints;
	private SheetClassDC SheetClassDC;
	private SheetPerception sheetPerception;
	private SheetSkils sheetSkills;
	// TODO weapons
	private int speed;

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}
}
