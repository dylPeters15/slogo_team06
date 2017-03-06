/**
 * 
 */
package frontend;

import java.util.ResourceBundle;

import javafx.scene.layout.Region;

/**
 * @author Dylan Peters
 *
 */
public abstract class UIChild<D extends UIChildDelegate> {
	private D delegate;

	public UIChild() {
		this(null, null);
	}

	public UIChild(ResourceBundle language) {
		this(null, language);
	}

	public UIChild(D delegate) {
		this(delegate, null);
	}

	public UIChild(D delegate, ResourceBundle language) {
		setLanguageResourceBundle(language);
		setDelegate(delegate);
	}

	public D getDelegate() {
		return delegate;
	}

	public void setDelegate(D delegate) {
		this.delegate = delegate;
	}

	public void setLanguageResourceBundle(ResourceBundle language) {

	}

	public abstract Region getRegion();
}
