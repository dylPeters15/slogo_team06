package frontend.simulation;

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
 * components necessary to allow the user to interact with the Simulation Environment display.
 * 
 * @author Andreas
 *
 */
class EnvironmentDisplayManager {
	
	private ScrollPane myEnvironment;
//	private Canvas myEnvironmentDisplay;
//	private GraphicsContext gc;
	private int width;
	private int height;
	private static final String TURTLE_IMAGE = "turtleicon.png";
	
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
	Region getRegion() {
		return myEnvironment;
	}
	
	
	
	private void initialize(){
		myEnvironment = new ScrollPane();
		Pane stack = new Pane();
		stack.prefWidthProperty().set(1000);
		stack.prefHeightProperty().set(1000);
		myEnvironment.setContent(stack);
		
//		myEnvironmentDisplay = new Canvas(Double.MAX_VALUE, Double.MAX_VALUE);
//		gc = myEnvironmentDisplay.getGraphicsContext2D();
//		stack.getChildren().add(myEnvironmentDisplay);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(TURTLE_IMAGE));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		imageView.setX(0);
		imageView.setY(0);
		stack.getChildren().add(imageView);
		myEnvironment.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myEnvironment.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myEnvironment.setOnScrollStarted(event -> didScroll());
		myEnvironment.setPrefSize(400, 400);
		myEnvironment.layout();
		myEnvironment.setHvalue(0.5);
		myEnvironment.layout();
		myEnvironment.setVvalue(0.5);
		
		
		
		Line line = new Line(0, 150, 200,150);   
		line.setStrokeWidth(20); 
		line.setStroke(Color.web("000000")); 
		stack.getChildren().add(line);
		
		Line line2 = new Line(200,150,200,300);
		line2.setStrokeWidth(10);
		line2.setStroke(Color.web("000000")); 
		stack.getChildren().add(line2);

		
		//gc.drawImage(imageView.getImage(), width/2-image.getWidth()/2, height/2-image.getHeight()/2);
	}
	
	void didScroll(){
		if (myEnvironment.getHvalue() == 1.0 || myEnvironment.getHvalue() == 0.0){
			double val = myEnvironment.getHvalue();
			((Region)(myEnvironment.getContent())).setPrefWidth(((Region)(myEnvironment.getContent())).getPrefWidth()*2);
			myEnvironment.layout();
			myEnvironment.setHvalue(0.25+val/2);
			recalcChildren(oldwidth,oldheight,newwidth,newheight);
		}
		if (myEnvironment.getVvalue() == 1.0 || myEnvironment.getVvalue() == 0.0){
			double val = myEnvironment.getVvalue();
			((Region)(myEnvironment.getContent())).setPrefHeight(((Region)(myEnvironment.getContent())).getPrefHeight()*2);
			myEnvironment.layout();
			myEnvironment.setVvalue(0.25+val/2);
			recalcChildren(oldwidth,oldheight,newwidth,newheight);
		}
	}
	
	private void recalcChildren(aasdfasdfasdf){
		for (Node child : ((Pane)(myEnvironment.getContent())).getChildren()){
			if (child instanceof ImageView){
				
			} else if (child instanceof Line){
				Line childLine = (Line)child;
				childLine.setStartX(asdf);
				childLine.setStartY(asdf);
				childLine.setEndX(asdf);
				childLine.setEndY(asdf);
			}
		}
	}
	

	void setBackgroundColor(Color color) {
		String t = color.toString().substring(2, color.toString().length());
		String s = String.format("-fx-background-color: #%s", t);
		myEnvironment.setStyle(s);	
	}
	
}
