/**
 * 
 */
package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backend.states.State;
import backend.states.StatesList;


/**
 * @author User
 *
 */
public class Forward extends Command {

	public Forward(StatesList<State> list) {
		super(list);
	}

	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));
	
	private final int NUM_PARAMS = 1;
	
	/*
	public double runCommand(List<String> param) {
		//TODO check params
		Double val = Double.parseDouble(param.get(0));
		return runCommand(val);
	}
	*/
	
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}
	
	@Override
	public double runCommand(double distance) {
		State newState = getNewState();
		newState.getActor().moveForward(distance);
		addNewState(newState);
		return distance;		
	}

	@Override
	Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public double runCommand() {
		//Throw error
		return 0;
	}

	@Override
	public double runCommand(double a, double b) {
		// TODO Auto-generated method stub
		return 0;
	}



}
