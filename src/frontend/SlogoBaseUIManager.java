/**
 * 
 */
package frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Parent;

/**
 * @author Dylan Peters
 *
 */
public abstract class SlogoBaseUIManager<D extends EmptyDelegate, T extends Parent>
		extends SlogoBaseObjectManager<D, T> implements UsesStyleSheets {
	private static final String STYLESHEET_RESOURCE_POINTER = "resources.styles/StylePointer";
	private static final String STYLE_RESOURCE_LIST = "resources.styles/StyleFileList";
	private static final String DEFAULT_KEY = "DefaultSyleSheet";

	private ObservableMap<String, String> styleSheetNamesAndFileNames;
	private String styleSheet;

	public SlogoBaseUIManager() {
		styleSheet = createDefaultStyleSheet();
		styleSheetNamesAndFileNames = createPossibleStyleSheets();
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
		return (UnmodifiableObservableMap<String, String>) FXCollections.unmodifiableObservableMap(styleSheetNamesAndFileNames);
	}

	protected void styleSheetDidChange() {

	}

	private String createDefaultStyleSheet() {
		return ResourceBundle.getBundle(STYLESHEET_RESOURCE_POINTER).getString(DEFAULT_KEY);
	}
	
	private ObservableMap<String, String> createPossibleStyleSheets(){
		Map<String, String> map = new HashMap<String,String>();
		ResourceBundle bundle = ResourceBundle.getBundle(STYLE_RESOURCE_LIST);
		for (String key : bundle.keySet()){
			map.put(key, bundle.getString(key));
		}
		return FXCollections.observableMap(map);
	}
}
