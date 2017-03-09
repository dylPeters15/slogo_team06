/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

/**
 * This interface allows the EditorPaneManager to 
 * @author Dylan Peters
 *
 */
public interface EditorPaneManagerDelegate {

	void userDidRequestChangeToLanguage(ResourceBundle newLanguage);

	void userDidRequestChangeToStylesheet(String stylesheet);

}
