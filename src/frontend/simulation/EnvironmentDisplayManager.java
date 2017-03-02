package frontend.simulation;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a Node object that has all of the UI
 * components necessary to allow the user to interact with the Simulation
 * Environment display.
 * 
 * @author Andreas
 *
 */
class EnvironmentDisplayManager {

	private ScrollPane myScrollPane;
	private Pane myPane;
	// private int width;
	// private int height;
	// private ImageView imageView;
	// private static final String TURTLE_IMAGE = "turtleicon.png";
	private TurtleView myTurtle;

	EnvironmentDisplayManager(int width, int height) {
		// this.width = width;
		// this.height = height;
		initialize();
	}

	TurtleView getTurtle() {
		return myTurtle;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	Region getRegion() {
		return myScrollPane;
	}

	private void initialize() {
		myScrollPane = new ScrollPane();
		myPane = new Pane();
		myPane.prefWidthProperty().set(1000);
		myPane.prefHeightProperty().set(1000);
		myScrollPane.setContent(myPane);

		myTurtle = new TurtleView();
		myTurtle.setPosition(convertXCoordinate(myTurtle.getX()),
				convertYCoordinate(myTurtle.getY()));
		myPane.getChildren().add(myTurtle.getImageView());

		myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setOnScroll(event -> didScroll());
		myScrollPane.setPrefSize(400, 400);
		myScrollPane.layout();
		myScrollPane.setHvalue(0.5);
		myScrollPane.layout();
		myScrollPane.setVvalue(0.5);

	}

	void updateTurtle() {
		drawLine(myTurtle.getPreviousX(), myTurtle.getPreviousY(),
				convertXCoordinate(myTurtle.getX()),
				convertYCoordinate(myTurtle.getY()));
		myTurtle.setPosition(convertXCoordinate(myTurtle.getX()),
				convertYCoordinate(myTurtle.getY()));
	}

	private void drawLine(double startX, double startY, double endX, double endY) {
		Line line = new Line(startX, startY, endX, endY);
		myPane.getChildren().add(line);
	}

	private double convertXCoordinate(double x) {
		return myPane.getPrefWidth() / 2 + x;
	}

	private double convertYCoordinate(double y) {
		return myPane.getPrefHeight() / 2 - y;
	}

	void didScroll() {
		if (myScrollPane.getHvalue() == 1.0 || myScrollPane.getHvalue() == 0.0) {
			double val = myScrollPane.getHvalue();
			double oldWidth = myPane.getPrefWidth();
			myPane.setPrefWidth(myPane.getPrefWidth() * 2);
			double newWidth = myPane.getPrefWidth();
			myScrollPane.layout();
			myScrollPane.setHvalue(0.25 + val / 2);
			recalcChildren(oldWidth, myPane.getHeight(), newWidth,
					myPane.getHeight());
		}
		if (myScrollPane.getVvalue() == 1.0 || myScrollPane.getVvalue() == 0.0) {
			double val = myScrollPane.getVvalue();
			double oldHeight = myPane.getPrefHeight();
			myPane.setPrefHeight(myPane.getPrefHeight() * 2);
			double newHeight = myPane.getPrefHeight();
			myScrollPane.layout();
			myScrollPane.setVvalue(0.25 + val / 2);
			recalcChildren(myPane.getWidth(), oldHeight, myPane.getWidth(),
					newHeight);
		}
	}

	private void recalcChildren(double oldWidth, double oldHeight,
			double newWidth, double newHeight) {
		double oldCenterX = oldWidth / 2;
		double oldCenterY = oldHeight / 2;
		double newCenterX = newWidth / 2;
		double newCenterY = newHeight / 2;
		for (Node child : ((Pane) (myScrollPane.getContent())).getChildren()) {
			if (child instanceof ImageView) {
				ImageView childImage = (ImageView) child;
				childImage.setX(newCenterX + (childImage.getX() - oldCenterX));
				childImage.setY(newCenterY + (childImage.getY() - oldCenterY));
			} else if (child instanceof Line) {
				Line childLine = (Line) child;
				childLine.setStartX(newCenterX
						+ (childLine.getStartX() - oldCenterX));
				childLine.setStartY(newCenterY
						+ (childLine.getStartY() - oldCenterY));
				childLine.setEndX(newCenterX
						+ (childLine.getEndX() - oldCenterX));
				childLine.setEndY(newCenterY
						+ (childLine.getEndY() - oldCenterY));
			}
		}
		if (!myPane.getChildren().contains(myTurtle.getImageView())) {
			myPane.getChildren().add(myTurtle.getImageView());
		}
	}

	void setBackgroundColor(Color color) {
		String t = color.toString().substring(2, color.toString().length());
		String s = String.format("-fx-background-color: #%s", t);
		myPane.setStyle(s);
	}

	void setTurtleImage(Image image) {
		myTurtle.setImage(image);
	}

	void home() {
		myScrollPane.layout();
		myScrollPane.setHvalue(myTurtle.getImageView().getX()
				/ myPane.getWidth());
		myScrollPane.layout();
		myScrollPane.setVvalue(myTurtle.getImageView().getY()
				/ myPane.getHeight());
	}

}
