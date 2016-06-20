package jaws.business.presets;

import java.util.Set;

import org.json.JSONObject;

import jaws.business.config.Config;

final public class PresetFactory {
	
	private PresetFactory() {}
	
	public static Preset loadPreset(String name) {
		return null; // TODO: load preset
	}
	
	public static void savePreset(Preset preset) {
		// TODO: save preset
	}
	
	public static Preset createPreset(String name) {
		return new Preset(name);
	}
	
	public static Set<String> getPresetNames() {
		return null; // TODO: get preset names
	}

	public static void deletePreset(String name) {
		// TODO: delete preset
	}
	
	public static Preset presetFromJSON(String name, JSONObject json) {
		
		Preset preset = createPreset(name);
		
		preset.setConfig(Config.from(json.getJSONObject("config")));
		preset.setLogLevel(json.getInt("logLevel"));
		preset.setLogTags(json.getString("logTags"));
		
		return preset;
	}
}
