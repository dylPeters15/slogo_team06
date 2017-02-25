/**
 * 
 */
package frontend.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

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
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";

	private Map<String, String> languageToPropertyName = new HashMap<String, String>();

	private ResourceBundle myResources;
	private String language;

	private TerminalDisplayDelegate delegate;

	private String prompt;

	private SplitPane overallSplitPane;
	private ScrollPane scrollPane;
	private VBox vbox;
	private ObservableList<TextInputArea> textInputAreas;
	private Button run, clear, clearAll;

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values to
	 * default.
	 */
	TerminalDisplayManager() {
		this(DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language with which to display the text in the terminal
	 *            display
	 */
	TerminalDisplayManager(String language) {
		this(null, language);
	}

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values except
	 * delegate to default.
	 * 
	 * @param delegate
	 *            the object implementing the TerminalDisplayDelegate interface
	 *            that this class will use to call delegated methods.
	 */
	TerminalDisplayManager(TerminalDisplayDelegate delegate) {
		this(delegate, DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values except
	 * delegate and language to default.
	 * 
	 * @param delegate
	 *            the object implementing the TerminalDisplayDelegate interface
	 *            that this class will use to call delegated methods.
	 * @param language
	 *            the language with which to display the text in the terminal
	 *            display
	 */
	TerminalDisplayManager(TerminalDisplayDelegate delegate, String language) {
		setDelegate(delegate);
		populateLanguageMap();
		setLanguage(language);
		initialize();
	}

	/**
	 * Changes the language that the Terminal Display uses to display its
	 * contents. The display will use a resource file with the words in that
	 * language to populate its contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	void setLanguage(String language) {
		this.language = language;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ languageToPropertyName.get(language));
		prompt = myResources.getString("Prompt");
		if (run != null) {
			run.setText(myResources.getString("Run"));
		}
		if (clear != null) {
			clear.setText(myResources.getString("Clear"));
		}
		if (clearAll != null) {
			clearAll.setText(myResources.getString("ClearAll"));
		}
		if (textInputAreas != null && textInputAreas.size() > 0){
			textInputAreas.get(textInputAreas.size()-1).setPrompt(prompt);
		}
	}

	/**
	 * Gets the language that this class uses to display its contents. This
	 * class will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return a string representing the language to be displayed
	 */
	String getLanguage() {
		return language;
	}

	/**
	 * Sets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the Terminal Display.
	 * 
	 * @param delegate
	 *            the object implementing the TerminalDisplayDelegate interface
	 *            that this class will use as its delegate
	 */
	void setDelegate(TerminalDisplayDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the fields in the
	 * TerminalDisplay.
	 * 
	 * @return the object implementing the TerminalDisplayDelegate interface
	 *         that this class will use as its delegate
	 */
	TerminalDisplayDelegate getDelegate() {
		return delegate;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	Node getTerminalDisplay() {
		return overallSplitPane;
	}

	/**
	 * This method allows other classes to print text to the terminal display.
	 * The text is printed and treated differently than commands - the user
	 * cannot click on the text to execute it the way the user can with previous
	 * commands.
	 * 
	 * @param text
	 *            the text to print to the Terminal display.
	 */
	void printText(String text) {
		textInputAreas.add(new TextInputArea(text));
	}

	/**
	 * Prints the commands to the screen and executes them. This allows other
	 * classes to run commands and print them to the screen.
	 * 
	 * @param commands
	 *            commands to print and execute
	 */
	void runCommands(String commands) {
		// if (model != null) {
		// model.interpret(commands);
		// }
	}

	/**
	 * Returns all of the text currently held in the Terminal Display, including
	 * all prompts, user input, and output.
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

	private void populateLanguageMap() {
		languageToPropertyName.put("Zhōngwén", "Chinese");
		languageToPropertyName.put("English", "English");
		languageToPropertyName.put("Français", "French");
		languageToPropertyName.put("Deutsche", "German");
		languageToPropertyName.put("Italiano", "Italian");
		languageToPropertyName.put("Português", "Portuguese");
		languageToPropertyName.put("Russkiy", "Russian");
		languageToPropertyName.put("Español", "Spanish");
	}

	private void textInputAreasDidChange() {
		vbox.getChildren().clear();
		for (TextInputArea textInputArea : textInputAreas) {
			vbox.getChildren().add(textInputArea.getRegion());
			textInputArea.getRegion().setOnMouseClicked(
					event -> textInputAreas.get(textInputAreas.size() - 1)
							.setText(
									textInputAreas.get(
											textInputAreas.size() - 1)
											.getText()
											+ textInputArea.getText()));
			textInputArea.getRegion().prefWidthProperty()
					.bind(scrollPane.widthProperty());
			textInputArea.getRegion().prefHeightProperty()
					.bind(overallSplitPane.heightProperty().multiply(0.25));
			textInputArea.greyOut(true);
		}
		if (textInputAreas.size() > 0) {
			textInputAreas.get(textInputAreas.size() - 1).greyOut(false);
			textInputAreas.get(textInputAreas.size() - 1).getRegion()
					.setOnMouseClicked(event -> {
					});
		}
		scrollPane.layout();
		scrollPane.setVvalue(1.0);
	}

	private void initialize() {

		overallSplitPane = new SplitPane();
		overallSplitPane.setOrientation(Orientation.HORIZONTAL);
		overallSplitPane.setPrefSize(800, 600);
		overallSplitPane.setDividerPositions(0.9);

		scrollPane = new ScrollPane();
		overallSplitPane.getItems().add(scrollPane);

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
		TextInputArea textInput = new TextInputArea();
		textInput.setPrompt(prompt);
		textInputAreas.add(textInput);

		SplitPane buttonSplitPane = new SplitPane();
		overallSplitPane.getItems().add(buttonSplitPane);
		buttonSplitPane.setOrientation(Orientation.VERTICAL);
		buttonSplitPane.setDividerPositions(1.0 / 3, 2.0 / 3);

		run = new Button(myResources.getString("Run"));
		run.setPrefWidth(Double.MAX_VALUE);
		run.setPrefHeight(Double.MAX_VALUE);
		run.setWrapText(true);
		run.setOnAction(event -> runButtonPressed());
		buttonSplitPane.getItems().add(run);
		clear = new Button(myResources.getString("Clear"));
		clear.setPrefWidth(Double.MAX_VALUE);
		clear.setPrefHeight(Double.MAX_VALUE);
		clear.setWrapText(true);
		clear.setOnAction(event -> clearButtonPressed());
		buttonSplitPane.getItems().add(clear);
		clearAll = new Button(myResources.getString("ClearAll"));
		clearAll.setPrefWidth(Double.MAX_VALUE);
		clearAll.setPrefHeight(Double.MAX_VALUE);
		clearAll.setWrapText(true);
		clearAll.setOnAction(event -> clearAllPressed());
		buttonSplitPane.getItems().add(clearAll);

	}

	private void clearAllPressed() {
		textInputAreas.clear();
		TextInputArea textInput = new TextInputArea();
		textInput.setPrompt(prompt);
		textInputAreas.add(textInput);
	}

	private void clearButtonPressed() {
		if (textInputAreas.size() > 0) {
			textInputAreas.get(textInputAreas.size() - 1).setText("");
		}
	}

	private void runButtonPressed() {
		if (textInputAreas.size() > 0
				&& !textInputAreas.get(textInputAreas.size() - 1).getText()
						.isEmpty()) {
			runCommands(textInputAreas.get(textInputAreas.size() - 1).getText());
			TextInputArea textInput = new TextInputArea();
			textInput.setPrompt(prompt);
			textInputAreas.add(textInput);
		}
	}

}