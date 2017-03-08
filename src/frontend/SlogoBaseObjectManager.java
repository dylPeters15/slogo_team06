/**
 * 
 */
package frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import com.sun.javafx.collections.UnmodifiableObservableMap;

/**
 * @author Dylan Peters
 *
 */
public abstract class SlogoBaseObjectManager<D extends EmptyDelegate, T extends Object>
		implements Delegated<D>, ObjectManager<T>, LanguageResource {
	private static final String LANGUAGE_RESOURCE_POINTER = "resources.languages/LanguagePointer";
	private static final String LANGUAGE_RESOURCE_LIST = "resources.languages/LanguageFileList";
	private static final String DEFAULT_KEY = "DefaultLanguageResource";

	private ObservableMap<String, ResourceBundle> possibleLanguages;
	private D delegate;
	private ResourceBundle language;

	public SlogoBaseObjectManager() {
		delegate = createNonActiveDelegate();
		language = createDefaultResourceBundle();
		possibleLanguages = createPossibleLanguagesMap();
	}

	@Override
	public final void setDelegate(D delegate) {
		if (delegate == null) {
			delegate = createNonActiveDelegate();
		}
		this.delegate = delegate;
		delegateDidChange();
	}

	@Override
	public final D getDelegate() {
		return delegate;
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

	public final UnmodifiableObservableMap<String, ResourceBundle> getPossibleResourceBundleNamesAndResourceBundles() {
		return (UnmodifiableObservableMap<String, ResourceBundle>) FXCollections.unmodifiableObservableMap(possibleLanguages);
	}

	protected void delegateDidChange() {

	}

	protected void languageResourceBundleDidChange() {

	}

	private ResourceBundle createDefaultResourceBundle() {
		return ResourceBundle.getBundle(ResourceBundle.getBundle(
				LANGUAGE_RESOURCE_POINTER).getString(DEFAULT_KEY));
	}

	private ObservableMap<String, ResourceBundle> createPossibleLanguagesMap() {
		Map<String, ResourceBundle> map = new HashMap<String, ResourceBundle>();
		ResourceBundle bundle = ResourceBundle
				.getBundle(LANGUAGE_RESOURCE_LIST);
		for (String key : bundle.keySet()) {
			map.put(key, ResourceBundle.getBundle(bundle.getString(key)));
		}
		return FXCollections.observableMap(map);
	}
}
