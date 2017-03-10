package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class XCoordinate extends Command {
	
	private final int NUM_PARAMS = 0;
	
	/**
	 * @param list
	 */
	public XCoordinate(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return x coordinate of the turtle from the center of the screen
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getActors().getPos().getX();
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
