/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import backend.Model;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
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
class TerminalDisplayManager extends EditorPaneManagerChild<TerminalDisplayDelegate>{
	private String prompt;

	private SplitPane overallSplitPane;
	private ScrollPane scrollPane;
	private VBox vbox;
	private ObservableList<TextInputArea> textInputAreas;
	private Button run, clear, clearAll;

	/**
	 * Creates a new instance of TerminalDisplayManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language with which to display the text in the terminal
	 *            display
	 */
	TerminalDisplayManager(ResourceBundle language) {
		this(null, language);
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
	TerminalDisplayManager(TerminalDisplayDelegate delegate, ResourceBundle language) {
		super(delegate,language);
		initialize(language);
	}

	/**
	 * Changes the language that the Terminal Display uses to display its
	 * contents. The display will use a resource file with the words in that
	 * language to populate its contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	@Override
	void setLanguageResourceBundle(ResourceBundle language) {
		prompt = language.getString("Prompt");
		if (run != null) {
			run.setText(language.getString("Run"));
		}
		if (clear != null) {
			clear.setText(language.getString("Clear"));
		}
		if (clearAll != null) {
			clearAll.setText(language.getString("ClearAll"));
		}
		if (textInputAreas != null && textInputAreas.size() > 0){
			textInputAreas.get(0).setPrompt(prompt);
		}
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	@Override
	Region getRegion() {
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
		addTextArea(new TextInputArea(text));
	}

	/**
	 * Prints the commands to the screen and executes them. This allows other
	 * classes to run commands and print them to the screen.
	 * 
	 * @param commands
	 *            commands to print and execute
	 */
	void runCommands(String commands) {
		 if (getDelegate() != null){
			 getDelegate().processCommand(commands);
		 }
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

	private void textInputAreasDidChange() {
		vbox.getChildren().clear();
		for (TextInputArea textInputArea : textInputAreas) {
			vbox.getChildren().add(textInputArea.getRegion());
			textInputArea.getRegion().setOnMouseClicked(
					event -> textInputAreas.get(0)
							.setText(
									textInputAreas.get(0)
											.getText()
											+ textInputArea.getText()));
			textInputArea.getRegion().prefWidthProperty()
					.bind(scrollPane.widthProperty());
			textInputArea.getRegion().prefHeightProperty()
					.bind(overallSplitPane.heightProperty().multiply(0.25));
			textInputArea.greyOut(true);
		}
		if (textInputAreas.size() > 0) {
			textInputAreas.get(0).greyOut(false);
			textInputAreas.get(0).getRegion()
					.setOnMouseClicked(event -> {
					});
		}
		scrollPane.layout();
		scrollPane.setVvalue(0.0);
	}
	
	private void addTextArea(TextInputArea textInputArea){
		textInputAreas.add(0, textInputArea);
	}

	private void initialize(ResourceBundle language) {
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
		addTextArea(textInput);

		SplitPane buttonSplitPane = new SplitPane();
		overallSplitPane.getItems().add(buttonSplitPane);
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
		TextInputArea textInput = new TextInputArea();
		textInput.setPrompt(prompt);
		addTextArea(textInput);
	}

	private void clearButtonPressed() {
		if (textInputAreas.size() > 0) {
			textInputAreas.get(0).setText("");
		}
	}

	private void runButtonPressed() {
		if (textInputAreas.size() > 0
				&& !textInputAreas.get(0).getText()
						.isEmpty()) {
			runCommands(textInputAreas.get(0).getText());
			TextInputArea textInput = new TextInputArea();
			textInput.setPrompt(prompt);
			addTextArea(textInput);
		}
	}

}
