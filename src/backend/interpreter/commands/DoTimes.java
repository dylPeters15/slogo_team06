package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class DoTimes extends Command {
	
	private final int NUM_PARAMS = 2;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Variable","Constant", "ListStart", "Commands", "ListEnd"}));
	private int upperLimit;
	private int index = 0;
	private List<String> commandToRun;
	private boolean nested = true;
	private String var;
	
	
	/**
	 * @param list
	 */
	public DoTimes(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(java.util.List)
	 */
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		checkIfEmpty(words);
		
		if(index == 0){	
			var = words.get(0);			
			upperLimit =  (int) Double.parseDouble(words.get(1));
			commandToRun =  new ArrayList<String>();
			addCommandToRun(commandToRun,words,2);	
		}
		
		index++;
		getVariables().put(var, Integer.toString(index) );
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
