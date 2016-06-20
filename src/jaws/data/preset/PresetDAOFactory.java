package jaws.data.preset;

public class PresetDAOFactory {
	
	public static PresetDAO createPresetDAO() {
		return new FilePresetDAO("presets.json");
	}
}
