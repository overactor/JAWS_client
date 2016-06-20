package jaws.data.preset;

import static trycrash.Try.tryCrash;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

class FilePresetDAO implements PresetDAO {
	
	File file;
	
	FilePresetDAO(String filePath) {
		
		file = new File(filePath);
		tryCrash(() -> file.createNewFile());
	}

	@Override
	public List<String> getPresetNames() {

		return null;
	}

	@Override
	public Optional<JSONObject> loadPreset(String name) {
		
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void savePreset(String name, JSONObject json) {
		
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePreset(String name) {
		
		// TODO Auto-generated method stub

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
}
