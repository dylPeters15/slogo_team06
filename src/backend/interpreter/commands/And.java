package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class And extends Command {
	
	private final int NUM_PARAMS = 2;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "Constant"}));

	/**
	 * @param list
	 */
	public And(StatesList<State> list) {
		super(list);
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(double, double)
	 */
	@Override
	public double runCommand(double a, double b) throws SlogoException {
		return (a!=0 && b!=0) ? 1 : 0;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
