/**
 * 
 */
package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;


/**
 * @author Tavo Loaiza
 *
 */
public class Right extends Left {

	/**
	 * @param list
	 */
	public Right(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Left#runCommand(double)
	 */
	@Override
	public double runCommand(double distance) throws SlogoException {
		return super.runCommand(-distance);
	}
	
	
}
