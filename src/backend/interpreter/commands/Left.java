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

	/**
	 * @param list
	 */
	public Left(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	private final int NUM_PARAMS = 1;
	
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(double)
	 */
	@Override
	public double runCommand(double distance) throws SlogoException {
		State newState = getNewState();
		newState.getActors().rotate(distance);
		addNewState(newState);
		return distance;		
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}


}
