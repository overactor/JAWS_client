package jaws.data.preset;

import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;

public interface PresetDAO {
	
	public Set<String> getPresetNames();
	
	public Optional<JSONObject> loadPreset(String name);
	public void savePreset(String name, JSONObject json);
	public void deletePreset(String name);
	
	public void exportPreset(JSONObject json);
	public Optional<JSONObject> importPreset();
}
