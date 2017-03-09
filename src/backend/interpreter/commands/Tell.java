package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Tell extends Command {
	
	private final int NUM_PARAMS = 3;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"ListStart", "Constants", "ListEnd"}));
	
	public Tell(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters: 0");
		}			
		if(words.get(0).contains("[") && words.get(words.size()-1).contains("]")){
			int id = 1;
			List<Integer> active = new ArrayList<Integer>();
			for(int i=1; i<words.size()-1; i++){
				//Rounds double down to integer
				id = (int) Double.parseDouble(words.get(i).trim());
				active.add(id);
			}
			State newState = getNewState();
			newState.setActiveList(active);
			addNewState(newState);
			return id;
		}	
		throw new SlogoException("IncorrectNumOfBrackets");
	}	
		
	/**
	 * @return 1 if the pen is down, 0 if the pen is up
	 */
	@Override
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}

	@Override
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}

	@Override
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectParamType");
	}
	
	@Override
	public boolean needsVarParams(){
		return true;
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
