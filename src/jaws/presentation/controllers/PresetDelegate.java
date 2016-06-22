package jaws.presentation.controllers;

/**
 * Contains callbacks for preset related actions
 * 
 * @author Rik
 */
public interface PresetDelegate {

	/**
	 * Called when the load button is clicked
	 */
	public void loadPresetClicked();
	
	/**
	 * Called when the save button is clicked
	 */
	public void savePresetClicked();
	
	/**
	 * Called when the delete button is clicked
	 */
	public void deletePresetClicked();
	
	/**
	 * Called when the new button is clicked
	 */
	public void newPresetClicked();
}
