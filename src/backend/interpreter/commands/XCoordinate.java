package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class XCoordinate extends Command {
	
	private final int NUM_PARAMS = 0;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{}));

	public XCoordinate(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return x coordinate of the turtle from the center of the screen
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getLastState().getActor().getPos().getX();
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
