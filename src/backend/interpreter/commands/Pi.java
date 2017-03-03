package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Pi extends Command {
	
	private final int NUM_PARAMS = 0;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{}));

	public Pi(StatesList<State> list) {
		super(list);
	}

	@Override
	public double runCommand() throws SlogoException {
		return Math.PI;
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
