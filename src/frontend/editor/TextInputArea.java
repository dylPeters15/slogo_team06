/**
 * 
 */
package frontend.editor;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import frontend.SlogoBaseUIManager;

/**
 * TextInputArea manages a TextArea object, and allows it to be covered with a
 * translucent pane to prevent the user from editing the text and to signal to
 * the user that the text is not editable (rather than simply making the
 * TextArea not editable). Furthermore, it encapsulates the TextArea within the
 * framework of the SlogoBaseUIManager that the rest of the front end classes
 * use.
 * 
 * @author Dylan Peters
 *
 */
class TextInputArea extends SlogoBaseUIManager<Region> {

	StackPane stackPane;
	TextArea textArea;
	Pane glassPane;

	/**
	 * Creates a new instance of TextInputArea. Sets all values to default. The
	 * text and the prompt text in the TextArea are both set to empty.
	 */
	TextInputArea() {
		this("", "");
	}

	/**
	 * Creates a new instance of TextInputArea. Sets the text and the prompt to
	 * the values specified in the constructor.
	 * 
	 * @param text
	 *            a String specifying the text to populate the TextArea with.
	 * @param prompt
	 *            a String specifying the prompt text to add to the TextArea.
	 *            The prompt text is shown when no text has been typed into the
	 *            TextArea.
	 */
	TextInputArea(String text, String prompt) {
		initStackPane();
		initTextArea(text, prompt);
		initGlassPane();
		initMouseEvents();
	}

	/**
	 * Sets the prompt of the textArea to the specified value.
	 * 
	 * @param prompt
	 *            a String specifying the prompt text to add to the TextArea.
	 *            The prompt text is shown when no text has been typed into the
	 *            TextArea.
	 */
	void setPrompt(String prompt) {
		textArea.setPromptText(prompt);
	}

	/**
	 * Sets the text of the textArea to the specified value.
	 * 
	 * @param text
	 *            a String specifying the text to populate the TextArea with.
	 */
	void setText(String text) {
		textArea.setText(text);
	}

	/**
	 * Gets the text that is contained in the TextArea.
	 * 
	 * @return a String representing the text currently in the TextArea.
	 */
	String getText() {
		return textArea.getText();
	}

	/**
	 * Gets the Region object managed by this class. The region object returned
	 * by this method should have all the UI functionality required for the user
	 * to interact with the TextArea unless the TextArea is covered by the
	 * translucent pane.
	 * 
	 * @return region object that has all the UI functionality required for the
	 *         user to interact with the TextArea unless the TextArea is covered
	 *         by the translucent pane.
	 */
	@Override
	public Region getObject() {
		return stackPane;
	}

	/**
	 * Called by other classes in the same package to toggle whether the
	 * TextArea is covered. This indicates whether the TextArea is editable by
	 * the user.
	 * 
	 * @param grey
	 *            boolean indicating whether the TextArea should be covered. if
	 *            grey is true, then the TextArea gets covered, otherwise, it
	 *            gets uncovered.
	 */
	void greyOut(boolean grey) {
		if (grey) {
			textArea.setEditable(false);
			if (!stackPane.getChildren().contains(glassPane)) {
				stackPane.getChildren().add(glassPane);
			}
		} else {
			textArea.setEditable(true);
			if (stackPane.getChildren().contains(glassPane)) {
				stackPane.getChildren().remove(glassPane);
			}
		}
	}

	private void initStackPane() {
		stackPane = new StackPane();
	}

	private void initTextArea(String text, String prompt) {
		textArea = new TextArea(text);
		textArea.setPromptText(prompt);
		bindRegionToRegion(textArea, stackPane);
		textArea.setWrapText(true);
		stackPane.getChildren().add(textArea);
	}

	private void initGlassPane() {
		glassPane = new Pane();
		glassPane
				.setBackground(new Background(new BackgroundFill((Paint) Color
						.color(0.5, 0.5, 0.5, 0.4), new CornerRadii(0),
						new Insets(0))));
		bindRegionToRegion(glassPane, stackPane);
	}

	private void bindRegionToRegion(Region regionToBeBound,
			Region regionToBeObserved) {
		regionToBeBound.prefWidthProperty().bind(
				regionToBeObserved.widthProperty());
		regionToBeBound.prefHeightProperty().bind(
				regionToBeObserved.heightProperty());
		regionToBeBound.translateXProperty().bind(
				regionToBeObserved.translateXProperty());
		regionToBeBound.translateYProperty().bind(
				regionToBeObserved.translateYProperty());
	}

	private void initMouseEvents() {
		stackPane.setOnMouseEntered(event -> glassPane
				.setBackground(new Background(new BackgroundFill((Paint) Color
						.color(0.8, 0.8, 0.8, 0.4), new CornerRadii(0),
						new Insets(0)))));
		stackPane.setOnMouseExited(eevent -> glassPane
				.setBackground(new Background(new BackgroundFill((Paint) Color
						.color(0.5, 0.5, 0.5, 0.4), new CornerRadii(0),
						new Insets(0)))));
		stackPane.setOnMousePressed(event -> glassPane
				.setBackground(new Background(new BackgroundFill((Paint) Color
						.color(0.1, 0.8, 0.2, 0.4), new CornerRadii(0),
						new Insets(0)))));
		stackPane.setOnMouseReleased(event -> glassPane
				.setBackground(new Background(new BackgroundFill((Paint) Color
						.color(0.5, 0.5, 0.5, 0.4), new CornerRadii(0),
						new Insets(0)))));
	}

}
