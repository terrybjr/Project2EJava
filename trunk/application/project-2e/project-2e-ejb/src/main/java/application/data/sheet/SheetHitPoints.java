package application.data.sheet;

import java.util.List;

import application.utils.MiscUtils;
import lombok.Data;

@Data
public class SheetHitPoints {
	private int current;
	private int temporary;
	private int dying;
	private int wounded;
	private List<String> resistances;
	private List<String> immunities;
	private List<String> conditions;
}
