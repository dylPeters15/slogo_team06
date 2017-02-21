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
 * This class sets up and manages a table to be used in the EditorPaneManager.
 * The goal of this class is to hide the details of implementation required to
 * display the names and values of all the variables currently in use in the
 * simulation. This class can hold an object that implements the
 * VariableDisplayManager interface. It will call the methods of that interface
 * when a variable is changed by the user, to alert the rest of the program
 * about the changes.
 * 
 * @author Dylan Peters
 *
 */
interface VariableDisplayManager {

	// Constructors that will be present when this interface is turned into a
	// class:
	// VariableDisplayManager();
	// VariableDisplayManager(String language);
	// VariableDisplayManager(VariableDisplayDelegate delegate);
	// VariableDisplayManager(VariableDisplayDelegate delegate, String
	// language);

	/**
	 * Sets the language that this class uses to display its contents. It will
	 * use a resource file with the words in that language to populate its
	 * contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	void setLanguage(String language);

	/**
	 * Gets the language that this class uses to display its contents. This
	 * class will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return a string representing the language to be displayed
	 */
	String getLanguage();

	/**
	 * Sets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the VariableDisplay.
	 * 
	 * @param delegate
	 *            the object implementing the VariableDisplayDelegate interface
	 *            that this class will use as its delegate
	 */
	void setDelegate(VariableDisplayDelegate delegate);

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the fields in the
	 * VariableDisplay.
	 * 
	 * @return the object implementing the VariableDisplayDelegate interface
	 *         that this class will use as its delegate
	 */
	VariableDisplayDelegate getDelegate();

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	Node getVariableDisplay();

}