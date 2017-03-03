package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Remainder extends Command {
	
	private final int NUM_PARAMS = 2;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "Constant"}));

	public Remainder(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(double a, double b) throws SlogoException {
		return (int)a % (int)b;
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
