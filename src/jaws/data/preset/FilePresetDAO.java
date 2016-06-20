package jaws.data.preset;

import static trycrash.Try.tryCatch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.json.JSONObject;

class FilePresetDAO implements PresetDAO {
	
	private File file;
	private Supplier<Optional<File>> saveFilePicker;
	private Supplier<Optional<File>> openFilePicker;
	
	FilePresetDAO(String filePath, Supplier<Optional<File>> saveFilePicker, Supplier<Optional<File>> openFilePicker) {		
		file = new File(filePath);
		this.saveFilePicker = saveFilePicker;
		this.openFilePicker = openFilePicker;
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
		
		saveFilePicker.get()
		              .ifPresent(file -> saveJSON(json, file));
	}

	@Override
	public Optional<JSONObject> importPreset() {
		
		return openFilePicker.get()
		                     .flatMap(this::getJSON);
	}

	private Optional<JSONObject> getJSON() {
		return getJSON(file);
	}
	
	private Optional<JSONObject> getJSON(File file) {
		
		return tryCatch(() -> {
			file.createNewFile();
			return new JSONObject(new String(Files.readAllBytes(file.toPath())));
		});
	}
	
	private void saveJSON(JSONObject json) {
		saveJSON(json, file);
	}
		
	private void saveJSON(JSONObject json, File file) {
		
		try (PrintWriter out = new PrintWriter(file)) {
			file.createNewFile();
			out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
