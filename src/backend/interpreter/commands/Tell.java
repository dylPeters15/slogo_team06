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
	
	/**
	 * @param list
	 */
	public Tell(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(java.util.List)
	 */
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		checkIfEmpty(words);
		
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
		
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#needsVarParams()
	 */
	@Override
	public boolean needsVarParams(){
		return true;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
