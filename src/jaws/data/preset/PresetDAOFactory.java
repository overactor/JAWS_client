package jaws.data.preset;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A class to manage {@link jaws.data.preset.PresetDAO PresteDAO objects}
 * 
 * @author Rik
 */
public class PresetDAOFactory {
	
	/**
	 * Creates a PresetDAO with a give save- and openFilePicker
	 * 
	 * @param saveFilePicker is called when a Preset is exported
	 * @param openFilePicker is called when a Preset is imported
	 * @return
	 */
	public static PresetDAO createPresetDAO(Supplier<Optional<File>> saveFilePicker, Supplier<Optional<File>> openFilePicker) {
		return new FilePresetDAO("../presets/presets.json", saveFilePicker, openFilePicker);
	}
}
