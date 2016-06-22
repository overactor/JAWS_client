package jaws.presentation.controllers;

/**
 * Contains callbacks for config related actions
 * 
 * @author Rik
 */
public interface ConfigDelegate {

	/**
	 * Called when the reset button is clicked
	 */
	public void resetClicked();
	
	/**
	 * Called when the apply button is clicked
	 */
	public void applyClicked();
	
	/**
	 * clicked when the restart button is clicked
	 */
	public void onOffClicked();
}
