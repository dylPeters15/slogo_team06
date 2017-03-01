package frontend.simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a Node object that has all of the UI
 * components necessary to allow the user to interact with the Simulation Environment display.
 * 
 * @author Andreas
 *
 */
class EnvironmentDisplayManager {
	
	private StackPane myEnvironment;
	private Canvas myEnvironmentDisplay;
	private GraphicsContext gc;
	private int width;
	private int height;
	private static final String TURTLE_IMAGE = "turtle.png";
	
	EnvironmentDisplayManager(int width, int height){
		this.width = width;
		this.height = height;
		initialize();
	}
	
	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	Node getRegion() {
		return myEnvironment;
	}
	
	
	
	private void initialize(){
		myEnvironment = new StackPane();
		myEnvironmentDisplay = new Canvas(width, height);
		gc = myEnvironmentDisplay.getGraphicsContext2D();
		myEnvironment.getChildren().add(myEnvironmentDisplay);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(TURTLE_IMAGE));
		gc.drawImage(image, width/2-image.getWidth()/2, height/2-image.getHeight()/2);
		
	}

	void setBackgroundColor(Color color) {
		String t = color.toString().substring(2, color.toString().length());
		String s = String.format("-fx-background-color: #%s", t);
		myEnvironment.setStyle(s);	
	}
	
}
