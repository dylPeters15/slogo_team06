/**
 * 
 */
package backend.states;

/**
 * @author Tavo
 *
 */


public class TurtleModel extends ActorModel {
	
	TurtleModel(ActorModel model){
		super(model);
		
	}

	public TurtleModel() {
		setVector(DEF_POS.getY(),DEF_POS.getY(),DEF_HEADING);
		
	}
}
