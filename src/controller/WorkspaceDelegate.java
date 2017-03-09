/**
 * 
 */
package controller;

import java.util.ResourceBundle;

/**
 * This interface is used by the Workspace in order to delegate work to another
 * class. Specifically, it allows the workspace to notify its delegate when the
 * workspace changes language. The delegate can then use this method to change
 * other aspects of the program to the same language.
 * 
 * @author Dylan Peters
 *
 */
public interface WorkspaceDelegate {

	/**
	 * This method allows the workspace to notify its delegate when the
	 * workspace changes language. It should be implemented by the delegate to
	 * change other aspects of the program to the same language if necessary.
	 * 
	 * @param workspace
	 *            the workspace that changed its language.
	 * @param newLanguage
	 *            the ResourceBundle containing the keys and values for the
	 *            language the workspace is now using.
	 */
	void didChangeLanguage(Workspace workspace, ResourceBundle newLanguage);

}
