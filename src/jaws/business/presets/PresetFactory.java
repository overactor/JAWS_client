package jaws.business.presets;

import java.util.List;

final public class PresetFactory {
	
	private PresetFactory() {}
	
	public static Preset loadPreset() {
		return null; // TODO: load preset
	}
	
	public static void savePreset(Preset preset) {
		// TODO: save preset
	}
	
	public static Preset createPreset(String name) {
		return new Preset(name);
	}
	
	public static List<String> getPresetNames() {
		return null; // TODO: get preset names
	}
}
