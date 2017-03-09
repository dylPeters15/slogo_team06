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

	/**
	 * @param list
	 */
	public SetPosition(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant, Constant"}));
	
	private final int NUM_PARAMS = 2;

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}


	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(double, double)
	 */
	@Override
	public double runCommand(double x, double y) throws SlogoException {
		State newState = getNewState();
		Double distance = newState.getActors().getPos().distance(x, y);
		newState.getActors().getPos().setLocation(x, y);
		addNewState(newState);
		return distance;
	}



}
