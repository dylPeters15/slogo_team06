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
	void didChangeToLanguage(ResourceBundle newLanguage);

	void didChangeToStylesheet(String stylesheet);
}
