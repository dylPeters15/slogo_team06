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
public class PenUp extends Command {

	public PenUp(StatesList<State> list) {
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
		addNewState(newState);
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 2");
	}



}