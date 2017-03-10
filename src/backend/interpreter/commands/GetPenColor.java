package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class GetPenColor extends Command {

	private final int NUM_PARAMS = 0;
	
	/**
	 * @param list
	 */
	public GetPenColor(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand()
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getPenColorList().getIndex();
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

}
