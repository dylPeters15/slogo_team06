package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class Repeat extends Command {
	
	private final int NUM_PARAMS = 4;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "ListStart", "Commands", "ListEnd"}));
	private int upperLimit;
	private int index = 0;
	private List<String> commandToRun;
	private boolean nested = true;
	
	
	/**
	 * @param list
	 */
	public Repeat(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(java.util.List)
	 */
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		checkIfEmpty(words);	
		if(index == 0){
			upperLimit =  (int) Double.parseDouble(words.get(0));		
			commandToRun =  new ArrayList<String>();
			addCommandToRun(commandToRun,words,1);	
		}		
		index++;
		getVariables().put("repcount", Integer.toString(index) );
		nested = index<upperLimit;

		return 0;
	}	
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#isNestedCommand()
	 */
	@Override
	public boolean isNestedCommand(){
		return nested;
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#nestedCommand()
	 */
	@Override
	public List<String> nestedCommand(){
		return commandToRun;
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
