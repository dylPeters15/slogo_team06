/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import Exceptions.SlogoException;
import backend.Model;
import frontend.SlogoBaseUIManager;
import frontend.help.HelpPaneManager;

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
public class EditorPaneManager extends
		SlogoBaseUIManager<EditorPaneManagerDelegate, Parent> implements
		EditorMenuBarDelegate, VariableDisplayDelegate, TerminalDisplayDelegate {

	private BorderPane borderPane;
	private TerminalDisplayManager terminalDisplayManager;
	private EditorMenuBarManager editorMenuBarManager;
	private VariableDisplayManager variableDisplayManager;

	private Model model;

	private Stage helpPaneStage;
	private HelpPaneManager helpPaneManager;

	public EditorPaneManager(Model model) {
		setModel(model);
		initialize();
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}

	/**
	 * Sets the width of the Parent object that holds all of the UI components.
	 * 
	 * @param width
	 *            width of the Parent object that holds all of the UI
	 *            components.
	 */
	public void setWidth(double width) {
		borderPane.setPrefWidth(width);
	}

	/**
	 * Gets the width of the Parent object that holds all of the UI components.
	 * 
	 * @return width of the Parent object that holds all of the UI components.
	 */
	public double getWidth() {
		return borderPane.getWidth();
	}

	/**
	 * Sets the height of the Parent object that holds all of the UI components.
	 * 
	 * @param height
	 *            height of the Parent object that holds all of the UI
	 *            components.
	 */
	public void setHeight(double height) {
		borderPane.setPrefHeight(height);
	}

	/**
	 * Gets the height of the Parent object that holds all of the UI components.
	 * 
	 * @return height of the Parent object that holds all of the UI components.
	 */
	public double getHeight() {
		return borderPane.getHeight();
	}

	/**
	 * Sets the language that this class uses to display its contents. It will
	 * use a resource file with the words in that language to populate its
	 * contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	@Override
	protected void languageResourceBundleDidChange() {
		terminalDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
		editorMenuBarManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
		variableDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
		helpPaneManager.setLanguageResourceBundle(getLanguageResourceBundle());

		model.setResourceBundle(getLanguageResourceBundle().getBaseBundleName());
		getDelegate().didChangeToLanguage(getLanguageResourceBundle());
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor. It can be used as the root of a Scene or
	 * added as a component in a larger display.
	 * 
	 * @return Parent containing all the UI components that allow the user to
	 *         interact with the Editor portion of the program
	 */
	@Override
	public Parent getObject() {
		return borderPane;
	}

	public void close() {
		helpPaneStage.close();
	}

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
	public void didSelectLanguage(ResourceBundle resourceBundle) {
		setLanguageResourceBundle(resourceBundle);
	}

	/**
	 * This is the implementation of the method in the EditorMenuBarDelegate
	 * interface.
	 * 
	 * This method is called when the user wants to see a list of the
	 * user-defined commands. This method displays all the commands the user has
	 * defined by printing them in the terminal portion of the display.
	 */
	public void seeUserDefinedCommands() {

	}

	/**
	 * This is the implementation of the method in the EditorMenuBarDelegate
	 * interface.
	 * 
	 * This method is called when the user wants to see a help page. It displays
	 * a list of all possible commands, as well as basic protocol about how to
	 * use the program by printing it to the terminal portion of the display.
	 */
	public void help() {
		if (helpPaneStage != null) {
			helpPaneStage.show();
			helpPaneStage.toFront();
		}
	}

	// VariableDisplayDelegate methods:

	/**
	 * This is the implementation of the method in the VariableDisplayDelegate
	 * interface.
	 * 
	 * This method is called when the user changes a variable via the
	 * VariableDisplayManager. It changes the value of the variable in the
	 * model, then updates the VariableDisplayManager to reflect the change.
	 */
	public void didChangeVariable(String variable, Object value) {

	}

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
	public void processCommand(String command) {
		if (model != null && !command.isEmpty()) {
			try {
				model.interpret(command);
			} catch (SlogoException e) {
				printError(e);
				displayErrorDialog(e);
			}
		}
	}

	@Override
	protected void styleSheetDidChange() {
		borderPane.getStylesheets().clear();
		borderPane.getStylesheets().add(getStyleSheet());
		helpPaneManager.setStyleSheet(getStyleSheet());
	}

	private void printError(SlogoException e) {
		terminalDisplayManager.printText(e.getText());
	}

	private void displayErrorDialog(SlogoException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText("Error Occurred");
		alert.setContentText("Error while Parsing Commands");

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(e.getText());
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setExpanded(true);

		alert.showAndWait();
	}

	private void initialize() {

		borderPane = new BorderPane();
		terminalDisplayManager = new TerminalDisplayManager();
		terminalDisplayManager.setDelegate(this);
		terminalDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
		editorMenuBarManager = new EditorMenuBarManager();
		editorMenuBarManager.setDelegate(this);
		editorMenuBarManager
				.setLanguageResourceBundle(getLanguageResourceBundle());

		variableDisplayManager = new VariableDisplayManager(
				model.getVariables());
		variableDisplayManager.setDelegate(this);
		variableDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());

		SplitPane terminalAndVarTable = new SplitPane();
		terminalAndVarTable.setOrientation(Orientation.HORIZONTAL);
		terminalAndVarTable.getItems().add(terminalDisplayManager.getObject());
		terminalAndVarTable.getItems().add(variableDisplayManager.getObject());
		terminalAndVarTable.setDividerPositions(0.8);

		borderPane.setCenter(terminalAndVarTable);
		borderPane.setTop(editorMenuBarManager.getObject());

		helpPaneStage = new Stage();
		helpPaneManager = new HelpPaneManager();
		helpPaneManager.setLanguageResourceBundle(getLanguageResourceBundle());
		helpPaneManager.setStyleSheet(getStyleSheet());
		helpPaneStage.setScene(new Scene(helpPaneManager.getObject()));

		setLanguageResourceBundle(getLanguageResourceBundle());

		setStyleSheet(getStyleSheet());
	}

	@Override
	public EditorPaneManagerDelegate createNonActiveDelegate() {
		return new EditorPaneManagerDelegate() {

			@Override
			public void didChangeToStylesheet(String stylesheet) {

			}

			@Override
			public void didChangeToLanguage(ResourceBundle newLanguage) {

			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * frontend.editor.EditorMenuBarDelegate#setStyleSheetTo(java.lang.String)
	 */
	@Override
	public void didSelectStyleSheet(String stylesheet) {
		setStyleSheet(stylesheet);

		getDelegate().didChangeToStylesheet(getStyleSheet());
	}

}
