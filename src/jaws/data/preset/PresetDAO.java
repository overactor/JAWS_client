package jaws.data.preset;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

public interface PresetDAO {
	
	public List<String> getPresetNames();
	
	public Optional<JSONObject> loadPreset(String name);
	public void savePreset(String name, JSONObject json);
	public void deletePreset(String name);
	
	public void exportPreset(JSONObject json);
	public Optional<JSONObject> importPreset();
}
