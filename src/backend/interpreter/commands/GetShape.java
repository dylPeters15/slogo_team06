package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class GetShape extends Command {
	
	private final int NUM_PARAMS = 0;

	public GetShape(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getTurtleShape().getIndex();
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
