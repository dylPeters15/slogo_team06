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

	/**
	 * @param list
	 */
	public Turtles(StatesList<State> list) {
		super(list);
	}
	
	private final int NUM_PARAMS = 0;

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand()
	 */
	@Override
	public double runCommand() throws SlogoException {
		return getLastState().getNumTurtles();
	}

}
