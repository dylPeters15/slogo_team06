package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class YCoordinate extends Command {

	private final int NUM_PARAMS = 0;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{}));

	public YCoordinate(StatesList<State> list) {
		super(list);
	}

	/**
	 * @return the heading of the turtle in degrees
	 */
	@Override
	public double runCommand() throws SlogoException {
		return this.getNewState().getActor().getPos().getY();
	}

	@Override
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
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
