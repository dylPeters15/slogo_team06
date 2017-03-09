package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class SetPenSize extends Command {
	
	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));

	public SetPenSize(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(double a) throws SlogoException {
		State newState = getNewState();
		newState.setPenSize(a);
		addNewState(newState);
		return a;
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
