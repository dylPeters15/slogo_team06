package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Home extends SetPosition {

	private final int NUM_PARAMS = 0;
	
	/**
	 * @param list
	 */
	/**
	 * @param list
	 */
	public Home(StatesList<State> list) {
		super(list);
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.SetPosition#numParamsNeeded()
	 */
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.SetPosition#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand()
	 */
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand()
	 */
	@Override
	public double runCommand() throws SlogoException {
		return super.runCommand(0,0);
	}
	
	
}
