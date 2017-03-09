/**
 * 
 */
package frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Parent;

import com.sun.javafx.collections.UnmodifiableObservableMap;

/**
 * @author Dylan Peters
 *
 */
public abstract class SlogoBaseUIManager<T extends Parent> implements
		ObjectManager<T>{
	private static final String LANGUAGE_RESOURCE_POINTER = "resources.languages/LanguagePointer";
	private static final String LANGUAGE_RESOURCE_LIST = "resources.languages/LanguageFileList";
	private static final String DEFAULT_LANGUAGE_KEY = "DefaultLanguageResource";
	private static final String STYLESHEET_RESOURCE_POINTER = "resources.styles/StylePointer";
	private static final String STYLE_RESOURCE_LIST = "resources.styles/StyleFileList";
	private static final String DEFAULT_STYLE_KEY = "DefaultSyleSheet";

	private ObjectProperty<ResourceBundle> language;
	private ObjectProperty<String> styleSheet;

	public SlogoBaseUIManager() {
		language = new SimpleObjectProperty<ResourceBundle>();
		language.setValue(createDefaultResourceBundle());
		styleSheet = new SimpleObjectProperty<String>();
		styleSheet.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				getObject().getStylesheets().clear();
				getObject().getStylesheets().add(newValue);
			}
		});
	}

	public ObjectProperty<ResourceBundle> getLanguage() {
		return language;
	}

	public ObjectProperty<String> getStyleSheet() {
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
			map.put(getLanguage().getValue().getString(key),
					fileBundle.getString(key));
		}
		return (UnmodifiableObservableMap<String, String>) FXCollections
				.unmodifiableObservableMap(FXCollections.observableMap(map));
	}

	public ResourceBundle createDefaultResourceBundle() {
		return ResourceBundle.getBundle(ResourceBundle.getBundle(
				LANGUAGE_RESOURCE_POINTER).getString(DEFAULT_LANGUAGE_KEY));
	}

	public String createDefaultStyleSheet() {
		return ResourceBundle.getBundle(STYLESHEET_RESOURCE_POINTER).getString(
				DEFAULT_STYLE_KEY);
	}

}
