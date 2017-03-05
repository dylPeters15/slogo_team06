package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Sine extends Command {

	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	public Sine(StatesList<State> list) {
		super(list);
	}
	
    /**
     * @param a: an angle represented as degree
     */
	@Override
	public double runCommand(double a) throws SlogoException {
		return Math.sin(Math.toRadians(a));
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
