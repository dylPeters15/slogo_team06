package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class ArcTangent extends Command {
	
	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));

	public ArcTangent(StatesList<State> list) {
		super(list);
	}

	/**
	 * @param a: tangent value of an angle
	 * @return: an angle represented as degree
	 */
	@Override
	public double runCommand(double a) throws SlogoException {
		return Math.toDegrees(Math.atan(a));
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
