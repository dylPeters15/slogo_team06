package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Heading extends Command {

	private final int NUM_PARAMS = 0;

	public Heading(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return the heading of the turtle in degrees
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getActors().getHeading();
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
