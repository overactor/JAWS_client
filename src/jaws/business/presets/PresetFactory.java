package jaws.business.presets;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.json.JSONObject;

import jal.business.log.LogLevel;
import jaws.business.config.Config;
import jaws.data.preset.PresetDAO;
import jaws.data.preset.PresetDAOFactory;

final public class PresetFactory {
	
	private static PresetDAO presetDAO;
	
	private PresetFactory() {}
	
	public static void init(Supplier<Optional<File>> saveFilePicker, Supplier<Optional<File>> openFilePicker) {
		presetDAO = PresetDAOFactory.createPresetDAO(saveFilePicker, openFilePicker);
	}
	
	public static void exportPreset(Preset preset) {
		presetDAO.exportPreset(preset.toJSON());
	}
	
	public static Optional<Preset> importPreset() {
		return presetDAO.importPreset().map(PresetFactory::presetFromJSON);
	}
	
	public static Optional<Preset> loadPreset(String name) {
		return presetDAO.loadPreset(name).map(PresetFactory::presetFromJSON);
	}
	
	public static void savePreset(Preset preset) {
		presetDAO.savePreset(preset.getName(), preset.toJSON());
	}
	
	public static Preset createPreset(String name) {
		return new Preset(name);
	}
	
	public static Set<String> getPresetNames() {
		return presetDAO.getPresetNames();
	}

	public static void deletePreset(String name) {
		presetDAO.deletePreset(name);
	}
	
	public static Preset presetFromJSON(JSONObject json) {
		
		Preset preset = createPreset(json.getString("name"));
		
		preset.setConfig(Config.from(json.getJSONObject("config")));
		preset.setLogLevel(LogLevel.withLevel(json.getInt("logLevel")));
		preset.setLogTags(json.getString("logTags"));
		
		return preset;
	}
}
