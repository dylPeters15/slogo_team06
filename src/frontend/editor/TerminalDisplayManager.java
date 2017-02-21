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
 * This class sets up and manages a Node object that has all of the UI
 * components necessary to allow the user to interact with the Terminal display.
 * The Terminal follows the Read-Eval-Print Loop that allows the user to type a
 * command, and upon hitting enter, executes that command. If the command is a
 * conditional or a loop, the user can enter the first portion of the loop, open
 * a bracket, and hit enter, and the user can then input commands until a
 * closing bracket is typed. For added functionality, the user can click on
 * previous commands to execute them again.
 * 
 * @author Dylan Peters
 *
 */
class TerminalDisplayManager {
	private static final String DEFAULT_LANGUAGE = "english";

	/**
	  * Creates a new instance of TerminalDisplayManager. Sets all values to default.
	  */
	 TerminalDisplayManager(){
		 this(DEFAULT_LANGUAGE);
	 }
	 
	 /**
	  * Creates a new instance of TerminalDisplayManager. Sets all values except language to default.
	  * @param language the language with which to display the text in the terminal display
	  */
	 TerminalDisplayManager(String language){
		 this(null,language);
	 }
	 
	 /**
	  * Creates a new instance of TerminalDisplayManager. Sets all values except delegate to default.
	  * @param delegate the object implementing the TerminalDisplayDelegate interface that this class will use to call delegated methods.
	  */
	 TerminalDisplayManager(TerminalDisplayDelegate delegate){
		 this(delegate,DEFAULT_LANGUAGE);
	 }
	 
	 /**
	  * Creates a new instance of TerminalDisplayManager. Sets all values except delegate and language to default.
	  * @param delegate the object implementing the TerminalDisplayDelegate interface that this class will use to call delegated methods.
	  * @param language the language with which to display the text in the terminal display
	  */
	 TerminalDisplayManager(TerminalDisplayDelegate delegate, String
	 language);

	/**
	 * Changes the language that the Terminal Display uses to display its
	 * contents. The display will use a resource file with the words in that
	 * language to populate its contents.
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
	 * methods are called when the user interacts with the Terminal Display.
	 * 
	 * @param delegate
	 *            the object implementing the TerminalDisplayDelegate interface
	 *            that this class will use as its delegate
	 */
	void setDelegate(TerminalDisplayDelegate delegate);

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the fields in the
	 * TerminalDisplay.
	 * 
	 * @return the object implementing the TerminalDisplayDelegate interface
	 *         that this class will use as its delegate
	 */
	TerminalDisplayDelegate getDelegate();

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	Node getTerminalDisplay();

	/**
	 * This method allows other classes to print text to the terminal display.
	 * The text is printed and treated differently than commands - the user
	 * cannot click on the text to execute it the way the user can with previous
	 * commands.
	 * 
	 * @param text
	 *            the text to print to the Terminal display.
	 */
	void printText(String text);

	/**
	 * Prints the commands to the screen and executes them. This allows other
	 * classes to run commands and print them to the screen.
	 * 
	 * @param commands
	 *            commands to print and execute
	 */
	void runCommands(String commands);

	/**
	 * Returns all of the text currently held in the Terminal Display, including
	 * all prompts, user input, and output.
	 * 
	 * @return all text currently in the Terminal Display.
	 */
	String getText();

}
