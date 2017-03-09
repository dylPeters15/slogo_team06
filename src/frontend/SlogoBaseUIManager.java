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
public abstract class SlogoBaseUIManager<T extends Parent> implements
		ObjectManager<T>, LanguageResource, UsesStyleSheets {
	private static final String LANGUAGE_RESOURCE_POINTER = "resources.languages/LanguagePointer";
	private static final String LANGUAGE_RESOURCE_LIST = "resources.languages/LanguageFileList";
	private static final String DEFAULT_LANGUAGE_KEY = "DefaultLanguageResource";
	private static final String STYLESHEET_RESOURCE_POINTER = "resources.styles/StylePointer";
	private static final String STYLE_RESOURCE_LIST = "resources.styles/StyleFileList";
	private static final String DEFAULT_STYLE_KEY = "DefaultSyleSheet";

	private ResourceBundle language;
	private String styleSheet;

	public SlogoBaseUIManager() {
		language = createDefaultResourceBundle();
		styleSheet = createDefaultStyleSheet();
	}

	@Override
	public final void setLanguageResourceBundle(ResourceBundle language) {
		if (language == null) {
			language = createDefaultResourceBundle();
		}
		this.language = language;
		languageResourceBundleDidChange();
	}

	@Override
	public final ResourceBundle getLanguageResourceBundle() {
		return language;
	}

	@Override
	public final void setStyleSheet(String stylesheet) {
		if (styleSheet == null) {
			styleSheet = createDefaultStyleSheet();
		}
		this.styleSheet = stylesheet;
		getObject().getStylesheets().clear();
		getObject().getStylesheets().add(this.styleSheet);
		styleSheetDidChange();
	}

	@Override
	public final String getStyleSheet() {
		return styleSheet;
	}

	public final UnmodifiableObservableMap<String, ResourceBundle> getPossibleResourceBundleNamesAndResourceBundles() {
		Map<String, ResourceBundle> map = new HashMap<String, ResourceBundle>();
		ResourceBundle bundle = ResourceBundle
				.getBundle(LANGUAGE_RESOURCE_LIST);
		for (String key : bundle.keySet()) {
			map.put(key, ResourceBundle.getBundle(bundle.getString(key)));
		}
		return (UnmodifiableObservableMap<String, ResourceBundle>) FXCollections
				.unmodifiableObservableMap(FXCollections.observableMap(map));
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

	protected void languageResourceBundleDidChange() {

	}

	protected void styleSheetDidChange() {

	}

	private ResourceBundle createDefaultResourceBundle() {
		return ResourceBundle.getBundle(ResourceBundle.getBundle(
				LANGUAGE_RESOURCE_POINTER).getString(DEFAULT_LANGUAGE_KEY));
	}

	private String createDefaultStyleSheet() {
		return ResourceBundle.getBundle(STYLESHEET_RESOURCE_POINTER).getString(
				DEFAULT_STYLE_KEY);
	}

}
