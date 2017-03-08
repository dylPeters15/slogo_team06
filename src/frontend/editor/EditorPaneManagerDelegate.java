/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import frontend.EmptyDelegate;

/**
 * @author Dylan Peters
 *
 */
public interface EditorPaneManagerDelegate extends EmptyDelegate {
	void didChangeToLanguage(ResourceBundle newLanguage);

	void didChangeToStylesheet(String stylesheet);
}
