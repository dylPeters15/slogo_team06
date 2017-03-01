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
public class PenDown extends Command {

	public PenDown(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	private final int NUM_PARAMS = 0;
	
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}
	
	@Override
	public double runCommand(double distance) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");	
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() throws SlogoException {
		State newState = getNewState();
		newState.getActor().setPenUp(false);
		addNewState(newState);
		return 1;
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 2");
	}



}
