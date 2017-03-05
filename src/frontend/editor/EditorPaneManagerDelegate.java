/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

/**
 * @author Dylan Peters
 *
 */
public interface EditorPaneManagerDelegate {
	void didChangeLanguage(EditorPaneManager editor, ResourceBundle newLanguage);
	void didChangeStylesheet(EditorPaneManager editor, String stylesheet);
}
