/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import frontend.SlogoBaseUIManager;

/**
 * This class sets up and manages a Parent object that has all of the UI
 * components necessary to allow the user to interact with the Terminal display.
 * The Terminal follows the Read-Eval-Print Loop that allows the user to type a
 * command, and upon hitting enter, executes that command. For added
 * functionality, the user can click on previous commands to execute them again.
 * 
 * In order for other classes to detect the commands that are to be run, the
 * TerminalDisplayManager holds the command that the user wants to execute in a
 * property called commandToRun. Other classes can view this property via the
 * getCommandToRun method, but they cannot modify the command to run. They can
 * add listeners and be notified when it changes (which occurs when the user
 * hits the "run" button).
 * 
 * @author Dylan Peters
 *
 */
class TerminalDisplayManager extends SlogoBaseUIManager<Parent> {
	private String prompt;

	private SplitPane overallSplitPane;
	private ScrollPane scrollPane;
	private VBox vbox;
	private ObservableList<TextInputArea> textInputAreas;
	private Button run, clear, clearAll;
	private StringProperty commandToRun;

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values to
	 * default.
	 * 
	 * @param language
	 *            the language with which to display the text in the terminal
	 *            display
	 */
	public TerminalDisplayManager() {
		commandToRun = new SimpleStringProperty("");
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> arg0,
					ResourceBundle arg1, ResourceBundle arg2) {
				languageResourceBundleDidChange();
			}
		});
		initialize();
	}

	/**
	 * Gets the StringProperty that contains the command the user currently
	 * wants to run. If no command has been selected to run, the String will be
	 * empty. The Property returned in this method is ReadOnly in order to
	 * prevent other classes from modifying it.
	 * 
	 * Client classes can add listeners to the commandToRun property to be
	 * notified when it changes, which occurs when the user clicks "run" on a
	 * new command.
	 * 
	 * @return a ReadOnlyStringProperty containing the command the user wants to
	 *         run.
	 */
	public ReadOnlyStringProperty getCommandToRun() {
		return commandToRun;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor.
	 * 
	 * @return Parent containing all the UI components that allow the user to
	 *         interact with the program
	 */
	@Override
	public Parent getObject() {
		return overallSplitPane;
	}

	/**
	 * Allows other classes to print text to the terminal display.
	 * 
	 * @param text
	 *            the text to print to the Terminal display.
	 */
	void printText(String text) {
		addTextArea(new TextInputArea(text, prompt));
	}

	/**
	 * Prints the commands to the screen and executes them. This allows other
	 * classes to run commands and print them to the screen.
	 * 
	 * @param commands
	 *            commands to print and execute
	 */
	void runCommands(String commands) {
		commandToRun.setValue(commands); // notifies listeners to run the new
											// command
		commandToRun.setValue(""); // set commandToRun to empty while it waits
									// for the user to enter new commands. This
									// ensures that if the user tries to run the
									// same command again, the client code that
									// is listening gets notified.
	}

	/**
	 * Returns all of the text currently held in the Terminal Display, including
	 * all prompts, error messages, user input, and output.
	 * 
	 * @return all text currently in the Terminal Display.
	 */
	String getText() {
		String text = "";
		for (TextInputArea textInputArea : textInputAreas) {
			text += textInputArea.getText();
		}
		return text;
	}

	/**
	 * Changes the language that the Terminal Display uses to display its
	 * contents. The display will use a resource file with the words in that
	 * language to populate its contents.
	 */
	private void languageResourceBundleDidChange() {
		prompt = getLanguage().getValue().getString("Prompt");
		run.setText(getLanguage().getValue().getString("Run"));
		clear.setText(getLanguage().getValue().getString("Clear"));
		clearAll.setText(getLanguage().getValue().getString("ClearAll"));
		if (textInputAreas.size() == 0) {
			addTextArea(new TextInputArea());
		}
		textInputAreas.get(0).setPrompt(prompt);
	}

	private void textInputAreasDidChange() {
		vbox.getChildren().clear();
		for (TextInputArea textInputArea : textInputAreas) {
			vbox.getChildren().add(textInputArea.getObject());
			textInputArea.getObject().setOnMouseClicked(
					event -> textInputAreas.get(0).setText(
							textInputAreas.get(0).getText()
									+ textInputArea.getText()));
			textInputArea.getObject().prefWidthProperty()
					.bind(scrollPane.widthProperty());
			textInputArea.getObject().prefHeightProperty()
					.bind(overallSplitPane.heightProperty().multiply(0.25));
			textInputArea.greyOut(true);
		}
		if (textInputAreas.size() > 0) {
			textInputAreas.get(0).greyOut(false);
			textInputAreas.get(0).getObject().setOnMouseClicked(event -> {
			});
		}
		scrollPane.layout();
		scrollPane.setVvalue(0.0);
	}

	private void addTextArea(TextInputArea textInputArea) {
		textInputAreas.add(0, textInputArea);
	}

	private void initialize() {
		overallSplitPane = new SplitPane();
		overallSplitPane.setOrientation(Orientation.HORIZONTAL);
		overallSplitPane.setPrefSize(800, 600);
		overallSplitPane.setDividerPositions(0.9);

		scrollPane = new ScrollPane();
		overallSplitPane.getItems().add(scrollPane);

		SplitPane buttonSplitPane = new SplitPane();
		overallSplitPane.getItems().add(buttonSplitPane);

		initializeScrollPane(scrollPane);
		initializeButtonPane(buttonSplitPane, getLanguage().getValue());
	}

	private void initializeScrollPane(ScrollPane scrollPane) {
		vbox = new VBox();
		vbox.prefWidthProperty().bind(scrollPane.widthProperty());
		scrollPane.setContent(vbox);

		textInputAreas = FXCollections.observableArrayList();
		textInputAreas.addListener(new ListChangeListener<TextInputArea>() {

			@Override
			public void onChanged(
					ListChangeListener.Change<? extends TextInputArea> change) {
				textInputAreasDidChange();
			}
		});
		addTextArea(new TextInputArea("", prompt));
	}

	private void initializeButtonPane(SplitPane buttonSplitPane,
			ResourceBundle language) {
		buttonSplitPane.setOrientation(Orientation.VERTICAL);
		buttonSplitPane.setDividerPositions(1.0 / 3, 2.0 / 3);

		run = new Button(language.getString("Run"));
		run.setPrefWidth(Double.MAX_VALUE);
		run.setPrefHeight(Double.MAX_VALUE);
		run.setWrapText(true);
		run.setOnAction(event -> runButtonPressed());
		buttonSplitPane.getItems().add(run);
		clear = new Button(language.getString("Clear"));
		clear.setPrefWidth(Double.MAX_VALUE);
		clear.setPrefHeight(Double.MAX_VALUE);
		clear.setWrapText(true);
		clear.setOnAction(event -> clearButtonPressed());
		buttonSplitPane.getItems().add(clear);
		clearAll = new Button(language.getString("ClearAll"));
		clearAll.setPrefWidth(Double.MAX_VALUE);
		clearAll.setPrefHeight(Double.MAX_VALUE);
		clearAll.setWrapText(true);
		clearAll.setOnAction(event -> clearAllPressed());
		buttonSplitPane.getItems().add(clearAll);
	}

	private void clearAllPressed() {
		textInputAreas.clear();
		addTextArea(new TextInputArea("", prompt));
	}

	private void clearButtonPressed() {
		if (textInputAreas.size() > 0) {
			textInputAreas.get(0).setText("");
		}
	}

	private void runButtonPressed() {
		if (textInputAreas.size() > 0) {
			runCommands(textInputAreas.get(0).getText());
			if (!textInputAreas.get(0).getText().isEmpty()) {
				addTextArea(new TextInputArea("", prompt));
			}
		}
	}
}