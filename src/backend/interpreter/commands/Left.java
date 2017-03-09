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
public class Left extends Command {

	public Left(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	private final int NUM_PARAMS = 1;
	
	
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}
	
	@Override
	public double runCommand(double distance) throws SlogoException {
		State newState = getNewState();
		newState.getActors().rotate(distance);
		addNewState(newState);
		return distance;		
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}


}
