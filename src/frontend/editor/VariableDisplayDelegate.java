/**
 * 
 */
package frontend.editor;

/**
 * 
 * This interface will be of default visibility, so it will only be visible to
 * other members of its package. Therefore, it will be part of the internal API
 * of the front end.
 * 
 * This interface (which is intended to be an interface in the implementation of
 * the project as well) is designed to allow the VariableDisplayManager to
 * communicate with the EditorPaneManager class when the user changes a variable
 * in the VariableDisplayManager.
 * 
 * The VariableDisplayManager will have an object that implements this
 * interface. (This object is referred to as the "delegate" because the
 * VariableDisplayManager "delegates" the implementation of these methods to the
 * class that implements the interface.) When a variable is changed, the
 * VariableDisplayManager will call the method associated with the changing
 * variable, which tells the delegate to perform some action that it has
 * implemented.
 * 
 * This pattern allows the VariableDisplayManager to set up the UI aspects
 * without having to directly implement the effects of changing variables.
 * Furthermore, it allows the class that implements this interface to be able to
 * implement the behavior without having to deal with the boilerplate code and
 * setup required to display a table of variables. This makes the code more
 * modular (easier to change the implementation of these methods) and more
 * encapsulated.
 * 
 * Additional methods can be written here to add functionality to the variable
 * display.
 * 
 * @author Dylan Peters
 *
 */
interface VariableDisplayDelegate extends EditorPaneManagerChildDelegate{

	/**
	 * This method is called when the user changes the value of a variable in
	 * the table displayed to him or her in the VariableDisplayManger. The class
	 * that implements this method should change the variable in the model and
	 * then update the VariableDisplayManager to reflect that change.
	 * 
	 * @param name
	 *            name of the variable being modified.
	 * @param value
	 *            the new value of the variable being modified.
	 */
	void didChangeVariable(String name, Object value);

}