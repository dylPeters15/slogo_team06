package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class IfElse extends Command {
	
	private final int NUM_PARAMS = 3;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "Constant", "Constant"}));

	public IfElse(StatesList<State> list) {
		super(list);
	}

	@Override
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}

	@Override
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}

	@Override
	public double runCommand(double condition, double command) throws SlogoException {
		return command;
	}	

	@Override
	public boolean needsPriorCheck() {
		return true;
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
