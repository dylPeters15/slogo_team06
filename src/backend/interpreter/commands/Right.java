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

	public Right(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(double distance) throws SlogoException {
		return super.runCommand(-distance);
	}
	
	
}
