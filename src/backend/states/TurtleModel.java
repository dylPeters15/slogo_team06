/**
 * 
 */
package backend.states;


/**
 * The subclass of ActorModel to represent the model of a turtle.
 * @author Tavo
 *
 */


public class TurtleModel extends ActorModel {
	
	/**
	 * Constructor of the TurtleModel class.
	 * @param model: an existing ActorModel.
	 */
	public TurtleModel(ActorModel model){
		super(model);
	}

	/**
	 * Default constructor of the TurtleModel class.
	 */
	public TurtleModel() {
		setVector(DEF_POS.getY(),DEF_POS.getY(),DEF_HEADING);
	}
}
