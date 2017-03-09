package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backend.states.State;
import backend.states.StatesList;

public class IfElse extends If {
	
	private final int NUM_PARAMS = 3;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "Constant", "Constant"}));

	/**
	 * @param list
	 */
	public IfElse(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.If#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.If#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
