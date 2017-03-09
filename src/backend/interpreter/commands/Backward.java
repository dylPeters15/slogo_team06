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
public class Backward extends Forward {

	/**
	 * @param list
	 */
	public Backward(StatesList<State> list) {
		super(list);
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Forward#runCommand(double)
	 */
	@Override
	public double runCommand(double distance) {
		return super.runCommand(-distance);
	}


}
