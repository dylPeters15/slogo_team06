package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class YCoordinate extends Command {

	private final int NUM_PARAMS = 0;

	/**
	 * @param list
	 */
	public YCoordinate(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return the heading of the turtle in degrees
	 */
	
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getActors().getPos().getY();
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
