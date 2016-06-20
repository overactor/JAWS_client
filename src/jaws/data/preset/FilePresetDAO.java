package jaws.data.preset;

import static trycrash.Try.tryCatch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;

class FilePresetDAO implements PresetDAO {
	
	File file;
	
	FilePresetDAO(String filePath) {		
		file = new File(filePath);
	}

	@Override
	public Set<String> getPresetNames() {
		return getJSON().orElseGet(JSONObject::new).keySet();
	}

	@Override
	public Optional<JSONObject> loadPreset(String name) {		
		return getJSON().map(json -> json.optJSONObject(name));
	}

	@Override
	public void savePreset(String name, JSONObject json) {
		
		saveJSON(getJSON().orElseGet(JSONObject::new)
		                  .put(name, json));
	}

	@Override
	public void deletePreset(String name) {
		
		JSONObject json = getJSON().orElseGet(JSONObject::new);
        json.remove(name);
        saveJSON(json);
	}

	@Override
	public void exportPreset(JSONObject json) {
		
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<JSONObject> importPreset() {
		
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	private Optional<JSONObject> getJSON() {
		
		return tryCatch(() -> {
			file.createNewFile();
			return new JSONObject(new String(Files.readAllBytes(file.toPath())));
		});
	}
	
	private void saveJSON(JSONObject json) {
		
		try (PrintWriter out = new PrintWriter(file)) {
			file.createNewFile();
			out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
