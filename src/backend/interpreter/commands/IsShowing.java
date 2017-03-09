package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class IsShowing extends Command {
	
	private final int NUM_PARAMS = 0;

	public IsShowing(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return 1 if the turtle is visible, 0 if the turtle is not visible
	 */
	@Override
	public double runCommand() throws SlogoException {
		return (this.getLastState().getActors().getVisible()) ? 1 : 0;
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}


}
