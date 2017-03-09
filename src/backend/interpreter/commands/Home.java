package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Home extends SetPosition {

	private final int NUM_PARAMS = 0;
	
	public Home(StatesList<State> list) {
		super(list);
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() throws SlogoException {
		return super.runCommand(0,0);
	}
	
	
}
