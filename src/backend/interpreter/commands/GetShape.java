package backend.interpreter.commands;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class GetShape extends Command {
	
	private final int NUM_PARAMS = 0;

	/**
	 * @param list
	 */
	public GetShape(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand()
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getTurtleShape().getIndex();
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
