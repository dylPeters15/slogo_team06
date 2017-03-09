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
public class Turtles extends Command {

	public Turtles(StatesList<State> list) {
		super(list);
	}
	
	private final int NUM_PARAMS = 0;

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() throws SlogoException {
		return getLastState().getNumTurtles();
	}

}
