/**
 * 
 */
package frontend.editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import Exceptions.SlogoException;
import backend.Model;
import frontend.SlogoBaseUIManager;

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
public class EditorPaneManager extends SlogoBaseUIManager<Parent> {

	private BorderPane borderPane;
	private TerminalDisplayManager terminalDisplayManager;
	private EditorMenuBarManager editorMenuBarManager;
	private VariableDisplayManager variableDisplayManager;

	private Model model;

	/**
	 * Creates a new instance of EditorPaneManager and sets all values to
	 * default.
	 * 
	 * @param model
	 *            the model that the editorPaneManager should update when the
	 *            user types a command
	 */
	public EditorPaneManager(Model model) {
		setModel(model);
		initialize();
	}

	/**
	 * Sets the model that the editorPaneManager should update when the user
	 * types a command
	 * 
	 * @param model
	 *            the model that the editorPaneManager should update when the
	 *            user types a command
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Gets the model that the editorPaneManager updates when the user types a
	 * command
	 * 
	 * @return the model that the editorPaneManager updates when the user types
	 *         a command
	 */
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

	/**
	 * Closes all the windows created by this EditorPaneManager
	 */
	public void closeAllChildWindows() {
		editorMenuBarManager.close();
	}

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
		terminalDisplayManager.getLanguage().setValue(getLanguage().getValue());

		editorMenuBarManager = new EditorMenuBarManager();
		editorMenuBarManager.getLanguage().setValue(getLanguage().getValue());

		variableDisplayManager = new VariableDisplayManager(
				model.getVariables());
		variableDisplayManager.getLanguage().setValue(getLanguage().getValue());

		SplitPane terminalAndVarTable = new SplitPane();
		terminalAndVarTable.setOrientation(Orientation.HORIZONTAL);
		terminalAndVarTable.getItems().add(terminalDisplayManager.getObject());
		terminalAndVarTable.getItems().add(variableDisplayManager.getObject());
		terminalAndVarTable.setDividerPositions(0.8);

		borderPane.setCenter(terminalAndVarTable);
		borderPane.setTop(editorMenuBarManager.getObject());

		getLanguage().bind(editorMenuBarManager.getLanguage());
		getStyleSheet().bind(editorMenuBarManager.getStyleSheet());

		terminalDisplayManager.getStyleSheet().bind(getStyleSheet());
		terminalDisplayManager.getLanguage().bind(getLanguage());
		variableDisplayManager.getStyleSheet().bind(getStyleSheet());
		variableDisplayManager.getLanguage().bind(getLanguage());

		editorMenuBarManager.getStyleSheet()
				.setValue(createDefaultStyleSheet());
		
		terminalDisplayManager.getCommandToRun().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				processCommand(newValue);
			}
		});
	}

}
