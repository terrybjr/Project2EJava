package application.data.sheet;

import java.util.List;

import application.utils.MiscUtils;
import lombok.Data;

@Data
public class SheetPerception {
	private int perception;
	private int wisBonus;
	private char proficiency;
	private int itemBonus;
	private List<String> senses;

}
