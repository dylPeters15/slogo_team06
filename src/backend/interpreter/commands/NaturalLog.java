package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class NaturalLog extends Command {
	
	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{ "Constant"}));

	public NaturalLog(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}

	@Override
	public double runCommand(double a) throws SlogoException {
		if (a > 0) {
			return Math.log(a);
		}
		else {
			throw new SlogoException("NegativeParameters");
		}
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 2");
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
