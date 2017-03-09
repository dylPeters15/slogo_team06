/**
 * 
 */
package frontend.editor;

/**
 * This interface will be of default visibility, so it will only be visible to
 * other members of its package. Therefore, it will be part of the internal API
 * of the front end.
 * 
 * This interface (which is intended to be an interface in the implementation of
 * the project as well) is designed to allow the TerminalDisplayManager to
 * communicate with the EditorPaneManager class when the user enters a command
 * in the TerminalDisplay.
 * 
 * The TerminalDisplayManager will have an object that implements this
 * interface. (This object is referred to as the "delegate" because the
 * TerminalDisplayManager "delegates" the implementation of these methods to the
 * class that implements the interface.) When a command is entered, the
 * TerminalDisplayManager will call the processCommand method of the delegate.
 * 
 * This pattern allows the TerminalDisplayManager to set up the UI aspects of
 * the Terminal without having to directly implement the effects of those
 * commands being processed. Furthermore, it allows the class that implements
 * this interface to be able to implement the behavior of processing the
 * commands without having to deal with the boilerplate code and setup required
 * to set up, display, and run the Terminal Display. This makes the code more
 * modular (easier to change the implementation of these methods) and more
 * encapsulated (don't have to know how the terminal is displayed).
 * 
 * Additional methods can be written here when new behavior is desired. For
 * example, this can be extended in order to add more functionality, such as the
 * ability to write a script and run it rather than simply use the
 * Read-Eval-Print Loop, or to allow the user to undo or redo a command.
 * 
 * @author Dylan Peters
 *
 */
interface TerminalDisplayDelegate {

	/**
	 * This method is called by the TerminalDisplayManager to indicate to the
	 * object implementing the interface that the user has entered a command
	 * into the terminal and hit enter, thus telling the program to execute the
	 * command. The delegate should implement the consequences of the command:
	 * parse the command, update the model's state based on the command, and
	 * then update the view so the user can see the command's effects.
	 * 
	 * @param command
	 *            the command the user has entered to be executed.
	 */
	void processCommand(String command);

}
