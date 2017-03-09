package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class IsPenDown extends Command {
	
	private final int NUM_PARAMS = 0;
	
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

	@Override
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
