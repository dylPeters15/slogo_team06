package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Tangent extends Command {

	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	public Tangent(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(double a) throws SlogoException {
		return Math.tan(Math.toRadians(a));
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
