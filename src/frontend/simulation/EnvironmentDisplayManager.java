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
	
	private ScrollPane myScrollPane;
	private Pane myPane;
	//private int width;
	//private int height;
	//private ImageView imageView;
	//private static final String TURTLE_IMAGE = "turtleicon.png";
	private TurtleView myTurtle;
	
	EnvironmentDisplayManager(int width, int height){
		//this.width = width;
		//this.height = height;
		initialize();
	}
	
	TurtleView getTurtle(){
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
	
	
	
	private void initialize(){
		myScrollPane = new ScrollPane();
		myPane = new Pane();
		myPane.prefWidthProperty().set(1000);
		myPane.prefHeightProperty().set(1000);
		myScrollPane.setContent(myPane);
		
		myTurtle = new TurtleView();
		myTurtle.setPosition(convertXCoordinate(myTurtle.getX()), convertYCoordinate(myTurtle.getY()));
		myPane.getChildren().add(myTurtle.getImageView());
		
		myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setOnScrollStarted(event -> didScroll());
		myScrollPane.setPrefSize(400, 400);
		myScrollPane.layout();
		myScrollPane.setHvalue(0.5);
		myScrollPane.layout();
		myScrollPane.setVvalue(0.5);		

	}
	
	void updateTurtle(){
		System.out.println("moved!");
		myTurtle.setPosition(convertXCoordinate(myTurtle.getX()), convertYCoordinate(myTurtle.getY()));
	}
	
	private void drawLine(double oldX, double oldY, double newX, double newY) {
		
	}
	
	private double convertXCoordinate(double x){
		return myPane.getPrefWidth()/2 + x;
	}
	
	private double convertYCoordinate(double y){
		return myPane.getPrefHeight()/2 - y;
	}
	
	void didScroll(){
		if (myScrollPane.getHvalue() == 1.0 || myScrollPane.getHvalue() == 0.0){
			double val = myScrollPane.getHvalue();
			((Region)(myScrollPane.getContent())).setPrefWidth(((Region)(myScrollPane.getContent())).getPrefWidth()*2);
			myScrollPane.layout();
			myScrollPane.setHvalue(0.25+val/2);
			//recalcChildren(oldwidth,oldheight,newwidth,newheight);
		}
		if (myScrollPane.getVvalue() == 1.0 || myScrollPane.getVvalue() == 0.0){
			double val = myScrollPane.getVvalue();
			((Region)(myScrollPane.getContent())).setPrefHeight(((Region)(myScrollPane.getContent())).getPrefHeight()*2);
			myScrollPane.layout();
			myScrollPane.setVvalue(0.25+val/2);
			//recalcChildren(oldwidth,oldheight,newwidth,newheight);
		}
	}
	
//	private void recalcChildren(aasdfasdfasdf){
//		for (Node child : ((Pane)(myScrollPane.getContent())).getChildren()){
//			if (child instanceof ImageView){
//				
//			} else if (child instanceof Line){
//				Line childLine = (Line)child;
//				childLine.setStartX(asdf);
//				childLine.setStartY(asdf);
//				childLine.setEndX(asdf);
//				childLine.setEndY(asdf);
//			}
//		}
//	}
	

	void setBackgroundColor(Color color) {
		String t = color.toString().substring(2, color.toString().length());
		String s = String.format("-fx-background-color: #%s", t);
		myPane.setStyle(s);	
	}

	void setTurtleImage(Image image) {
		myTurtle.setImage(image);
	}
	
}
