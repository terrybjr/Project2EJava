package application.data.sheet;

import java.util.ArrayList;

import application.entity.ref.RefAncestry;
import application.utils.MiscUtils;

public class SheetDemographics {
	private String characterName;
	private String playerName;
	private Long experiencePoints;
	private String ancestry;
	private String heritage;
	private String background;
	private ArrayList<String> classes;
	private String size;
	private String alignment;
	private String traits;
	private String deity;
	private int level;
	private int heroPoints;

	public String getCharacterName() {
		return this.characterName;
	}

	public void setCharacterName(final String pCharacterName) {
		this.characterName = pCharacterName;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(final String pPlayerName) {
		this.playerName = pPlayerName;
	}

	public Long getExperiencePoints() {
		return this.experiencePoints;
	}

	public void setExperiencePoints(final Long pExperiencePoints) {
		this.experiencePoints = pExperiencePoints;
	}

	public String getAncestry() {
		return this.ancestry;
	}

	public void setAncestry(final String pAncestry) {
		this.ancestry = pAncestry;
	}

	public String getHeritage() {
		return this.heritage;
	}

	public void setHeritage(final String pHeritage) {
		this.heritage = pHeritage;
	}

	public String getBackground() {
		return this.background;
	}

	public void setBackground(final String pBackground) {
		this.background = pBackground;
	}

	public ArrayList<String> getClasses() {
		return this.classes;
	}

	public void setClasses(final ArrayList<String> pClasses) {
		this.classes = pClasses;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(final String pSize) {
		this.size = pSize;
	}

	public String getAlignment() {
		return this.alignment;
	}

	public void setAlignment(final String pAlignment) {
		this.alignment = pAlignment;
	}

	public String getTraits() {
		return this.traits;
	}

	public void setTraits(final String pTraits) {
		this.traits = pTraits;
	}

	public String getDeity() {
		return this.deity;
	}

	public void setDeity(final String pDeity) {
		this.deity = pDeity;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(final int pLevel) {
		this.level = pLevel;
	}

	public int getHeroPoints() {
		return this.heroPoints;
	}

	public void setHeroPoints(final int pHeroPoints) {
		this.heroPoints = pHeroPoints;
	}

	@Override
	public String toString() {
		return MiscUtils.objToJson(this);
	}

}
