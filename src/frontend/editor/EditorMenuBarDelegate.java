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
 * the project as well) is designed to allow the EditorMenuBar to communicate
 * with the EditorPaneManager class when the user interacts with a Control
 * component in the EditorMenuBar.
 * 
 * The EditorMenuBarManager will have an object that implements this interface.
 * (This object is referred to as the "delegate" because the
 * EditorMenuBarManager "delegates" the implementation of these methods to the
 * class that implements the interface.) When a button is clicked or a combobox
 * is selected, the EditorMenuBarManager will call the method associated with
 * that button/combobox, which tells the delegate to perform some action that it
 * has implemented.
 * 
 * This pattern allows the EditorMenuBarManager to set up the UI aspects of the
 * menu bar (buttons to allow the user to run the code, comboboxes to select
 * language, etc.) without having to directly implement the effects of those
 * Control objects being interacted with. Furthermore, it allows the class that
 * implements this interface to be able to implement the behavior (changing the
 * language, running the code, etc.) without having to deal with the boilerplate
 * code and setup required to set up and display the menu bar. This makes the
 * code more modular (easier to change the implementation of these methods) and
 * more encapsulated (don't have to know how the buttons/comboboxes are
 * displayed).
 * 
 * Additional methods can be written here when other buttons are added to the
 * EditorMenuBarManager.
 * 
 * @author Dylan Peters
 *
 */
interface EditorMenuBarDelegate {

	/**
	 * This method is called when the user selects to change the language in
	 * which the program is displayed. The class implementing this method should
	 * change the language for every UI item being displayed on the screen.
	 * 
	 * @param language
	 *            the language to display the program in
	 */
	void didSelectLanguage(String language);

	/**
	 * This method is called when the user wants to see a list of the
	 * user-defined commands. The class implementing this interface should
	 * somehow display all the commands the user has defined.
	 */
	void seeUserDefinedCommands();

	/**
	 * This method is called when the user wants to see a help page. The class
	 * implementing this method should display a list of all possible commands,
	 * as well as basic protocol about how to use the program.
	 */
	void help();

}
