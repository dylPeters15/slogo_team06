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

/**
 * @author Dylan Peters
 *
 */
class TextInputArea {
	
	StackPane stackPane;
	TextArea textArea;
	Pane glassPane;
	
	TextInputArea(){
		this("");
	}
	
	TextInputArea(String text){
		stackPane = new StackPane();
		textArea = new TextArea(text);
		textArea.prefWidthProperty().bind(stackPane.widthProperty());
		textArea.prefHeightProperty().bind(stackPane.heightProperty());
		textArea.translateXProperty().bind(stackPane.translateXProperty());
		textArea.translateYProperty().bind(stackPane.translateYProperty());
		textArea.setWrapText(true);
		stackPane.getChildren().add(textArea);
		glassPane = new Pane();
		glassPane.setBackground(new Background(new BackgroundFill(
				 (Paint) Color.color(0.5, 0.5, 0.5, 0.4),
				 new CornerRadii(0), new Insets(0))));
		stackPane.setOnMouseEntered(event -> glassPane.setBackground(new Background(new BackgroundFill(
				 (Paint) Color.color(0.8, 0.8, 0.8, 0.4),
				 new CornerRadii(0), new Insets(0)))));
		stackPane.setOnMouseExited(eevent -> glassPane.setBackground(new Background(new BackgroundFill(
				 (Paint) Color.color(0.5, 0.5, 0.5, 0.4),
				 new CornerRadii(0), new Insets(0)))));
		stackPane.setOnMousePressed(event -> glassPane.setBackground(new Background(new BackgroundFill(
				 (Paint) Color.color(0.1, 0.8, 0.2, 0.4),
				 new CornerRadii(0), new Insets(0)))));
		stackPane.setOnMouseReleased(event -> glassPane.setBackground(new Background(new BackgroundFill(
				 (Paint) Color.color(0.5, 0.5, 0.5, 0.4),
				 new CornerRadii(0), new Insets(0)))));
		glassPane.prefWidthProperty().bind(stackPane.widthProperty());
		glassPane.prefHeightProperty().bind(stackPane.heightProperty());
		glassPane.translateXProperty().bind(stackPane.translateXProperty());
		glassPane.translateYProperty().bind(stackPane.translateYProperty());
	}
	
	void setPrompt(String prompt){
		textArea.setPromptText(prompt);
	}
	
	void setText(String text){
		textArea.setText(text);
	}

	String getText(){
		return textArea.getText();
	}
	Region getRegion(){
		return stackPane;
	}
	void greyOut(boolean grey){
		if (grey){
			textArea.setEditable(false);
			if (!stackPane.getChildren().contains(glassPane)){
				stackPane.getChildren().add(glassPane);
			}
		} else {
			textArea.setEditable(true);
			if (stackPane.getChildren().contains(glassPane)){
				stackPane.getChildren().remove(glassPane);
			}
		}
	}
	
}
