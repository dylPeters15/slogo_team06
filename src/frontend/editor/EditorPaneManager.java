/**
 * 
 */
package frontend.editor;

import javafx.scene.Parent;

/**
 * This class will be of public visibility, so it will be visible to any class
 * or interface in the program. Therefore, it will be part of the External API
 * of the front end.
 * 
 * This class is designed to encapsulate the set up of the display of the
 * editor/terminal functionality of the front end of the program. It creates a
 * Parent object and populates it with a menu bar, a terminal-style text entry
 * field, and a table with the values of the variables. That Parent object can
 * then be accessed via the getParent() method, to be used as the root of a
 * scene or to be added as a component in a larger display.
 * 
 * This class implements the EditorMenuBarDelegate and VariableDisplayDelegate
 * interfaces to allow the EditorMenuBarManager and VariableDisplayManager to
 * communicate with it when an event occurs.
 * 
 * This class can be extended in order to add more functionality, such as the
 * ability to write a script and run it rather than simply use the
 * Read-Eval-Print Loop, or to allow the user to undo or redo a command.
 * 
 * When this becomes a class it will implement EditorMenuBarDelegate,
 * VariableDisplayDelegate, and TerminalDisplayDelegate, but to make the
 * interface-representation of the class compile, it must "extend" the other
 * interfaces.
 * 
 * @author Dylan Peters
 *
 */
public interface EditorPaneManager extends EditorMenuBarDelegate,
		VariableDisplayDelegate, TerminalDisplayDelegate {

	// Constructors that will be present when this interface is turned into a
	// class:
	// public EditorPaneManager();
	// public EditorPaneManager(String language);
	// public EditorPaneManager(double width, double height);
	// public EditorPaneManager(double width, double height, String language);

	/**
	 * Sets the width of the Parent object that holds all of the UI components.
	 * 
	 * @param width
	 *            width of the Parent object that holds all of the UI
	 *            components.
	 */
	public void setWidth(double width);

	/**
	 * Gets the width of the Parent object that holds all of the UI components.
	 * 
	 * @return width of the Parent object that holds all of the UI components.
	 */
	public double getWidth();

	/**
	 * Sets the height of the Parent object that holds all of the UI components.
	 * 
	 * @param height
	 *            height of the Parent object that holds all of the UI
	 *            components.
	 */
	public void setHeight(double height);

	/**
	 * Gets the height of the Parent object that holds all of the UI components.
	 * 
	 * @return height of the Parent object that holds all of the UI components.
	 */
	public double getHeight();

	/**
	 * Sets the language that this class uses to display its contents. It will
	 * use a resource file with the words in that language to populate its
	 * contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	public void setLanguage(String language);

	/**
	 * Gets the language that this class uses to display its contents. This
	 * class will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return a string representing the language to be displayed
	 */
	public String getLanguage();

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor. It can be used as the root of a Scene or
	 * added as a component in a larger display.
	 * 
	 * @return Parent containing all the UI components that allow the user to
	 *         interact with the Editor portion of the program
	 */
	public Parent getParent();

	// EditorMenuBarDelegate methods:

	/**
	 * This is the implementation of the method in the EditorMenuBarDelegate
	 * interface.
	 * 
	 * This method is called when the user selects to change the language in
	 * which the program is displayed. It changes the language for every UI item
	 * being displayed on the screen.
	 * 
	 * @param language
	 *            the language to display the program in
	 */
	void didSelectLanguage(String language);

	/**
	 * This is the implementation of the method in the EditorMenuBarDelegate
	 * interface.
	 * 
	 * This method is called when the user wants to see a list of the
	 * user-defined commands. This method displays all the commands the user has
	 * defined by printing them in the terminal portion of the display.
	 */
	void seeUserDefinedCommands();

	/**
	 * This is the implementation of the method in the EditorMenuBarDelegate
	 * interface.
	 * 
	 * This method is called when the user wants to see a help page. It displays
	 * a list of all possible commands, as well as basic protocol about how to
	 * use the program by printing it to the terminal portion of the display.
	 */
	void help();

	// VariableDisplayDelegate methods:

	/**
	 * This is the implementation of the method in the VariableDisplayDelegate
	 * interface.
	 * 
	 * This method is called when the user changes a variable via the
	 * VariableDisplayManager. It changes the value of the variable in the
	 * model, then updates the VariableDisplayManager to reflect the change.
	 */
	void didChangeVariable(String variable, Object value);

	// TerminalDisplayDelegate methods:

	/**
	 * This is the implementation of the method in the TerminalDisplayDelegate
	 * interface.
	 * 
	 * This method is called by the TerminalDisplayManager to indicate to the
	 * object implementing the interface that the user has entered a command
	 * into the terminal and hit enter, thus telling the program to execute the
	 * command. It implements the consequences of the command: parse the
	 * command, update the model's state based on the command, and then update
	 * the view so the user can see the command's effects.
	 * 
	 * @param command
	 *            the command the user has entered to be executed.
	 */
	void processCommand(String command);
}
