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

/**
 * A class to manage {@link jaws.business.presets.Preset presets}
 * 
 * @author Rik
 */
final public class PresetFactory {
	
	private static PresetDAO presetDAO;
	
	private PresetFactory() {}
	
	/**
	 * Initializes the PresetFactory
	 * 
	 * @param saveFilePicker is called when a preset is exported
	 * @param openFilePicker is called when a preset is imported
	 */
	public static void init(Supplier<Optional<File>> saveFilePicker, Supplier<Optional<File>> openFilePicker) {
		presetDAO = PresetDAOFactory.createPresetDAO(saveFilePicker, openFilePicker);
	}
	
	/**
	 * Exports a preset 
	 * 
	 * @param preset the preset to export
	 */
	public static void exportPreset(Preset preset) {
		presetDAO.exportPreset(preset.toJSON());
	}
	
	/**
	 * Imports a preset
	 * 
	 * @return returns an imported preset, empty if no file was selected
	 */
	public static Optional<Preset> importPreset() {
		return presetDAO.importPreset().map(PresetFactory::presetFromJSON);
	}
	
	/**
	 * Loads a preset by name
	 * 
	 * @param name the name of the preset to load
	 * 
	 * @return the loaded preset, empty if it wasn't found
	 */
	public static Optional<Preset> loadPreset(String name) {
		return presetDAO.loadPreset(name).map(PresetFactory::presetFromJSON);
	}
	
	/**
	 * Saves a preset
	 * 
	 * @param preset the preset to save
	 */
	public static void savePreset(Preset preset) {
		presetDAO.savePreset(preset.getName(), preset.toJSON());
	}
	
	/**
	 * Creates a preset with a given name
	 * 
	 * @param name the name of the preset
	 * 
	 * @return the newly created preset
	 */
	public static Preset createPreset(String name) {
		return new Preset(name);
	}
	
	/**
	 * @return the names of all saved presets
	 */
	public static Set<String> getPresetNames() {
		return presetDAO.getPresetNames();
	}
	
	/**
	 * Deletes a preset by name
	 * 
	 * @param name the name of the preset to delete
	 */
	public static void deletePreset(String name) {
		presetDAO.deletePreset(name);
	}
	
	/**
	 * Creates a preset from a JSONObject
	 * 
	 * @param json the JSONObject to parse
	 * 
	 * @return the parsed preset
	 */
	public static Preset presetFromJSON(JSONObject json) {
		
		Preset preset = createPreset(json.getString("name"));
		
		preset.setConfig(Config.from(json.getJSONObject("config")));
		preset.setLogLevel(LogLevel.withLevel(json.getInt("logLevel")));
		preset.setLogTags(json.getString("logTags"));
		
		return preset;
	}
}
