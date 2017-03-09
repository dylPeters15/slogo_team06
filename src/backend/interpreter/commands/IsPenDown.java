package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class IsPenDown extends Command {
	
	private final int NUM_PARAMS = 0;
	
	/**
	 * @param list
	 */
	public IsPenDown(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return 1 if the pen is down, 0 if the pen is up
	 */
	@Override
	public double runCommand() throws SlogoException {
		return (!this.getLastState().getActors().getPenUp()) ? 1 : 0;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
