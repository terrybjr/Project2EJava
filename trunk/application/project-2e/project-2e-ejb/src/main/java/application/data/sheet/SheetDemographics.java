package application.data.sheet;

import java.util.ArrayList;

import application.entity.ref.RefAncestry;
import application.utils.MiscUtils;
import lombok.Data;

@Data
public class SheetDemographics {
	private String characterName;
	private String playerName;
	private String experiencePoints;
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
}
