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
public class Turtles extends Command {

	public Turtles(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{}));
	
	private final int NUM_PARAMS = 0;
	
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() throws SlogoException {
		return getLastState().getNumTurtles();
	}

}
