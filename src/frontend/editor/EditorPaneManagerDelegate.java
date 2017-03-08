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
	void didChangeToLanguage(ResourceBundle newLanguage);

	void didChangeToStylesheet(String stylesheet);
}
