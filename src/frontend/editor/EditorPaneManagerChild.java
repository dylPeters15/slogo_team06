/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import javafx.scene.layout.Region;

/**
 * @author Dylan Peters
 *
 */
abstract class EditorPaneManagerChild<D extends EditorPaneManagerChildDelegate> {

	private D delegate;
	
	EditorPaneManagerChild(){
		this(null,null);
	}
	EditorPaneManagerChild(ResourceBundle language){
		this(null,language);
	}
	EditorPaneManagerChild(D delegate){
		this(delegate,null);
	}
	EditorPaneManagerChild(D delegate, ResourceBundle language){
		setLanguageResourceBundle(language);
		setDelegate(delegate);
	}


	D getDelegate() {
		return delegate;
	}

	void setDelegate(D delegate) {
		this.delegate = delegate;
	}

	void setLanguageResourceBundle(ResourceBundle language) {

	}

	abstract Region getRegion();
	
}
