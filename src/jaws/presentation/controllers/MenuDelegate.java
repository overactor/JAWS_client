package jaws.presentation.controllers;

/**
 * Contains callbacks for actions in the main menu
 * 
 * @author Rik
 */
public interface MenuDelegate {

	/**
	 * Called when the connect item is clicked
	 */
	public void connectClicked();

	/**
	 * Called when the disconnect item is clicked
	 */
	public void disconnectClicked();

	/**
	 * Called when the import item is clicked
	 */
	public void importClicked();

	/**
	 * Called when the export item is clicked
	 */
	public void exportClicked();
}
