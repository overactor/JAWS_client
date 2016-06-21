package jaws.data.preset;

import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;

/**
 * An interface to represent Data Access to presets
 * 
 * @author Rik
 * 
 * @see jaws.data.preset.PresetDAOFactory
 * @see jaws.business.presets.PresetFactory
 */
public interface PresetDAO {
	
	/**
	 * @return the names of all saved presets
	 */
	public Set<String> getPresetNames();
	
	/**
	 * Loads a preset by name
	 * 
	 * @param name the name of the preset to load
	 * 
	 * @return the loaded preset as a JSONObject, empty if it wasn't found
	 */
	public Optional<JSONObject> loadPreset(String name);

	
	/**
	 * Saves a preset
	 * 
	 * @param json the preset to save as a JSONObject
	 */
	public void savePreset(String name, JSONObject json);

	
	/**
	 * Deletes a preset by name
	 * 
	 * @param name the name of the preset to delete
	 */
	public void deletePreset(String name);
	
	/**
	 * Exports a preset 
	 * 
	 * @param preset the preset to export as a JSONObject
	 */
	public void exportPreset(JSONObject json);

	
	/**
	 * Imports a preset
	 * 
	 * @return returns an imported preset as a JSONObject, empty if no file was selected
	 */
	public Optional<JSONObject> importPreset();
}
