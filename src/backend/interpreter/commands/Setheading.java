/**
 * 
 */
package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.ActorModel;
import backend.states.State;
import backend.states.StatesList;


/**
 * @author Tavo Loaiza
 *
 */
public class Setheading extends Command {

	public Setheading(StatesList<State> list) {
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
		State newState = getNewState();
		Double distance = Math.abs(newState.getActor().getHeading() - a)%360;
		newState.getActor().setHeading(a);
		addNewState(newState);
		return distance;
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

		return 0;
	}



}
