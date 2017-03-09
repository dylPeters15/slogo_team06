package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class GetPenColor extends Command {

	private final int NUM_PARAMS = 0;
	
	public GetPenColor(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getPenColorList().getIndex();
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
