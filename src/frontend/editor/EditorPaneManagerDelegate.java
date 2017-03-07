/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import frontend.UIChildDelegate;

/**
 * @author Dylan Peters
 *
 */
public interface EditorPaneManagerDelegate extends UIChildDelegate {
	void didChangeLanguage(EditorPaneManager editor, ResourceBundle newLanguage);

	void didChangeStylesheet(EditorPaneManager editor, String stylesheet);
}
