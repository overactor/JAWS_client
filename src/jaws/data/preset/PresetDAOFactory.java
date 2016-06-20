package jaws.data.preset;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

public class PresetDAOFactory {
	
	public static PresetDAO createPresetDAO(Supplier<Optional<File>> saveFilePicker, Supplier<Optional<File>> openFilePicker) {
		return new FilePresetDAO("presets.json", saveFilePicker, openFilePicker);
	}
}
