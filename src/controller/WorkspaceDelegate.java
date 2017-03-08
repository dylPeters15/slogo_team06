/**
 * 
 */
package controller;

import java.util.ResourceBundle;

/**
 * @author Dylan Peters
 *
 */
public interface WorkspaceDelegate {
	void didChangeLanguage(Workspace workspace, ResourceBundle newLanguage);
}
