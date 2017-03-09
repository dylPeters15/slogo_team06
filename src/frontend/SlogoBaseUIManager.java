/**
 * 
 */
package frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.Parent;

import com.sun.javafx.collections.UnmodifiableObservableMap;

/**
 * @author Dylan Peters
 *
 */
public abstract class SlogoBaseUIManager<D, T extends Parent> extends
		SlogoBaseObjectManager<D, T> implements UsesStyleSheets {
	private static final String STYLESHEET_RESOURCE_POINTER = "resources.styles/StylePointer";
	private static final String STYLE_RESOURCE_LIST = "resources.styles/StyleFileList";
	private static final String DEFAULT_KEY = "DefaultSyleSheet";

	private String styleSheet;

	public SlogoBaseUIManager() {
		styleSheet = createDefaultStyleSheet();
	}

	@Override
	public final void setStyleSheet(String stylesheet) {
		if (styleSheet == null) {
			styleSheet = createDefaultStyleSheet();
		}
		this.styleSheet = stylesheet;
		styleSheetDidChange();
	}

	@Override
	public final String getStyleSheet() {
		return styleSheet;
	}

	public final UnmodifiableObservableMap<String, String> getPossibleStyleSheetNamesAndFileNames() {
		Map<String, String> map = new HashMap<String, String>();
		ResourceBundle fileBundle = ResourceBundle
				.getBundle(STYLE_RESOURCE_LIST);
		for (String key : fileBundle.keySet()) {
			map.put(getLanguageResourceBundle().getString(key),
					fileBundle.getString(key));
		}
		return (UnmodifiableObservableMap<String, String>) FXCollections
				.unmodifiableObservableMap(FXCollections.observableMap(map));
	}

	protected void styleSheetDidChange() {

	}

	private String createDefaultStyleSheet() {
		return ResourceBundle.getBundle(STYLESHEET_RESOURCE_POINTER).getString(
				DEFAULT_KEY);
	}

}
