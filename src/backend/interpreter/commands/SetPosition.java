/**
 * 
 */
package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;


/**
 * @author Tavo Loaiza
 *
 */
public class SetPosition extends Command {

	public SetPosition(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant, Constant"}));
	
	private final int NUM_PARAMS = 2;

	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}
	
	@Override
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}

	@Override
	public double runCommand(double x, double y) {
		State newState = getNewState();
		Double distance = newState.getActor().getPos().distance(x, y);
		newState.getActor().getPos().setLocation(x, y);
		addNewState(newState);
		return distance;
	}



}
