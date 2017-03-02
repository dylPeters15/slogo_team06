package frontend.simulation;

import java.awt.geom.Point2D;

import backend.states.ActorModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class TurtleView extends ActorView {
	
	private static final String DEFAULT_TURTLE_IMAGE = "turtleicon.png";
	private static final int DEFAULT_DIMENSION = 50;
	
	private Image turtleImage;
	private ImageView turtleImageView;
	
	TurtleView(){
		turtleImage = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_TURTLE_IMAGE));
		turtleImageView = new ImageView(turtleImage);
		setDimensions(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
		setPosition(0, 0);
	}
	
	Image getImage(){
		return turtleImage;
	}
	
	void setImage(Image i){
		turtleImage = i;
		turtleImageView.setImage(turtleImage);;
	}
	
	ImageView getImageView(){
		return turtleImageView;
	}
	
	void setDimensions(int x, int y){
		turtleImageView.setFitWidth(x);
		turtleImageView.setFitHeight(y);
	}
	
	double getX(){
		return turtleImageView.getX();
	}
	
	double getY(){
		return turtleImageView.getY();
	}
	
	void setPosition(double x, double y){
		turtleImageView.setX(x);
		turtleImageView.setY(y);
	}
	
	void update(ActorModel a){
		System.out.println("updaated!");
		Point2D.Double newPoint = a.getPos();
		double oldX = getX();
		double oldY = getY();
		double newX = newPoint.getX();
		double newY = newPoint.getY();
		setPosition(newX, newY);
		//drawLine(oldX, oldY, newX, newY);
	}
	
}
