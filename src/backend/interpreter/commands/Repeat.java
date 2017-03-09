package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
	
	
	public Repeat(StatesList<State> list) {
		super(list);
	}
	
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
	
	@Override
	public boolean isNestedCommand(){
		return nested;
	}
	
	@Override
	public List<String> nestedCommand(){
		return commandToRun;
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
