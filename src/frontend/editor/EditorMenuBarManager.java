/**
 * 
 */
package frontend.editor;

import javafx.scene.Node;

/**
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a menu bar to be used in the
 * EditorPaneManager. The goal of this class is to hide the details of
 * implementation required to display buttons and other Control objects required
 * for the user to interact with in the editor. This class can hold an object
 * that implements the EditorMenuBarDelegate interface. It will call the methods
 * of that interface when Control components are changed by the user, to alert
 * the rest of the program about the changes.
 * 
 * @author Dylan Peters
 *
 */
class EditorMenuBarManager {
	private static final String DEFAULT_LANGUAGE = "english";

	/**
	 * Creates a new instance of EditorMenuBarManager. Sets all values to default.
	 */
	 public EditorMenuBarManager(){
		 this(DEFAULT_LANGUAGE);
	 }
	 
	 /**
	  * Creates a new instance of EditorMenuBarManager. Sets all values except language to default.
	  * @param language the language to use in the display of the menu bar.
	  */
	 public EditorMenuBarManager(String language){
		 this(null,language);
	 }
	 
	 /**
	  * Creates a new instance of EditorMenuBarManager. Sets all values except delegate to default.
	  * @param delegate the object implementing the EditorMenuBarDelegate interface that this class will use to call delegated methods.
	  */
	 public EditorMenuBarManager(EditorMenuBarDelegate delegate){
		 this(delegate,DEFAULT_LANGUAGE);
	 }
	 
	 /**
	  * Creates a new instance of EditorMenuBarManager. Sets all values except delegate and language to default.
	  * @param delegate the object implementing the EditorMenuBarDelegate interface that this class will use to call delegated methods.
	  * @param language the language to use in the display of the menu bar.
	  */
	 public EditorMenuBarManager(EditorMenuBarDelegate delegate, String
	 language);

	/**
	 * Sets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with one or more of the
	 * Control components in the EditorMenuBar.
	 * 
	 * @param delegate
	 *            the object implementing the EditorMenuBarDelegate interface
	 *            that this class will use as its delegate
	 */
	void setDelegate(EditorMenuBarDelegate delegate);

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with one or more of the
	 * Control components in the EditorMenuBar.
	 * 
	 * @return the object implementing the EditorMenuBarDelegate interface that
	 *         this class will use as its delegate
	 */
	EditorMenuBarDelegate getDelegate();

	/**
	 * Changes the language that the menu bar uses to display its contents. The
	 * menu bar will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	void setLanguage(String language);

	/**
	 * Gets the language that the menu bar uses to display its contents. The
	 * menu bar will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return a string representing the language to be displayed
	 */
	String getLanguage();

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	Node getMenuBar();

}
