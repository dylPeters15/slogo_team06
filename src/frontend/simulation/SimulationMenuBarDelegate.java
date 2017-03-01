package frontend.simulation;

import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * This interface will be of default visibility, so it will only be visible to
 * other members of its package. Therefore, it will be part of the internal API
 * of the front end.
 * 
 * This interface (which is intended to be an interface in the implementation of
 * the project as well) is designed to allow the SimulationMenuBarManager to communicate
 * with the SimulationPaneManager class when the user interacts with a Control
 * component in the SimulationMenuBar.
 * @author Andreas
 *
 */
interface SimulationMenuBarDelegate extends SimulationPaneManagerChildDelegate {
	
	/**
	 * This method is called to reset the turtle at
	 * the home location (this may potentially return
	 * a double value later)
	 */
	void home();
	
	/**
	 * This method is called to change the 
	 * Background color of the simulation's
	 * environment display
	 * @param color
	 */
	void setBackgroundColor(Color color);
	
	/**
	 * This method is called to change the image 
	 * used for the actor/turtle
	 * @param image
	 */
	void setTurtleImage(Image image);

}