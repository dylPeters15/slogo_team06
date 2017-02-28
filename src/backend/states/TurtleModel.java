/**
 * 
 */
package backend.states;

import javafx.scene.image.Image;

/**
 * @author Tavo
 *
 */


public class TurtleModel extends ActorModel {
	 public static final String TURTLE_IMAGE_PATH = "turtle.png";
	 
	 TurtleModel(){
		 super();
		 super.setActorImage(new Image(TURTLE_IMAGE_PATH));
	 }
	 
	 TurtleModel(double x, double y, double heading) {
		 super(x,y,heading);
		 super.setActorImage(new Image(TURTLE_IMAGE_PATH));
	 }
	 
	 TurtleModel(TurtleModel turtle) {
		 super(turtle);
		 super.setActorImage(new Image(TURTLE_IMAGE_PATH));
	 }
}
