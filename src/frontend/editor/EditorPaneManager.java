/**
 * 
 */
package frontend.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import Exceptions.SlogoException;
import backend.Model;
import frontend.help.HelpPaneManager;
import frontend.simulation.SimulationPaneManager;

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
public class EditorPaneManager implements EditorMenuBarDelegate,
		VariableDisplayDelegate, TerminalDisplayDelegate {
	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 600;
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";
	private static final String DEFAULT_STYLE_SHEET = "resources/default.css";

	private Map<String, String> languageToPropertyName = new HashMap<String, String>();

	private BorderPane borderPane;
	private TerminalDisplayManager terminalDisplayManager;
	private EditorMenuBarManager editorMenuBarManager;
	private VariableDisplayManager variableDisplayManager;
	private Stage simulationStage;

	private Model model;

	/**
	 * Creates a new instance of EditorPaneManager. Sets all values to default.
	 */
	public EditorPaneManager() {
		this(DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of EditorPaneManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language with which to display the text in the editor
	 *            pane.
	 */
	public EditorPaneManager(String language) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, language);
	}

	/**
	 * Creates a new instance of EditorPaneManager. Sets all values except width
	 * and height to default.
	 * 
	 * @param width
	 *            the width to display the editor pane.
	 * @param height
	 *            the height to display the editor pane.
	 */
	public EditorPaneManager(double width, double height) {
		this(width, height, DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of EditorPaneManager. Sets all values except
	 * width, height, and language to default.
	 * 
	 * @param width
	 *            the width to display the editor pane.
	 * @param height
	 *            the height to display the editor pane.
	 * @param language
	 *            the language with which to display the text in the editor
	 *            pane.
	 */
	public EditorPaneManager(double width, double height, String language) {
		populateLanguageMap();
		initialize(language);
		setLanguage(language);
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
	public void setLanguage(String language) {
		ResourceBundle myResources = ResourceBundle
				.getBundle(DEFAULT_RESOURCE_PACKAGE
						+ languageToPropertyName.get(language));
		terminalDisplayManager.setLanguageResourceBundle(myResources);
		editorMenuBarManager.setLanguageResourceBundle(myResources);
		variableDisplayManager.setLanguageResourceBundle(myResources);
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
	public Parent getParent() {
		return borderPane;
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
	public void didSelectLanguage(String language) {
		setLanguage(language);
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
		Stage helpStage = new Stage();
		helpStage.setScene(new Scene(new HelpPaneManager().getParent()));
		helpStage.show();
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
		if (model != null) {
			try {
				model.interpret(command);
			} catch (SlogoException e) {
				terminalDisplayManager.printText(e.getText());
				
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

				// Set expandable Exception into the dialog pane.
				alert.getDialogPane().setExpandableContent(expContent);
				alert.getDialogPane().setExpanded(true);

				alert.showAndWait();
			}
		}
		simulationStage.show();
	}

	public void setStyleSheet(String styleSheet) {
		borderPane.getStylesheets().clear();
		borderPane.getStylesheets().add(styleSheet);
	}

	private void populateLanguageMap() {
		languageToPropertyName.put("ZhÅ�ngwÃ©n", "Chinese");
		languageToPropertyName.put("English", "English");
		languageToPropertyName.put("FranÃ§ais", "French");
		languageToPropertyName.put("Deutsche", "German");
		languageToPropertyName.put("Italiano", "Italian");
		languageToPropertyName.put("PortuguÃªs", "Portuguese");
		languageToPropertyName.put("Russkiy", "Russian");
		languageToPropertyName.put("EspaÃ±ol", "Spanish");
	}

	private void initialize(String language) {
		model = new Model();

		ResourceBundle myResources = ResourceBundle
				.getBundle(DEFAULT_RESOURCE_PACKAGE
						+ languageToPropertyName.get(language));
		borderPane = new BorderPane();
		terminalDisplayManager = new TerminalDisplayManager(this, myResources);
		editorMenuBarManager = new EditorMenuBarManager(this, myResources);
		variableDisplayManager = new VariableDisplayManager(this, myResources);

		borderPane.setCenter(terminalDisplayManager.getRegion());
		borderPane.setRight(variableDisplayManager.getRegion());
		borderPane.setTop(editorMenuBarManager.getRegion());

		simulationStage = new Stage();
		SimulationPaneManager simulationPaneManager = new SimulationPaneManager();
		simulationStage.setScene(new Scene(simulationPaneManager.getParent()));

		setStyleSheet(DEFAULT_STYLE_SHEET);
	}
}
