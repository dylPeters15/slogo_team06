package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class For extends Command {
	
	private final int NUM_PARAMS = 2;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"ListStart", "Variable","Constant", "Constant","Constant", 
											"ListEnd", "ListStart", "Commands", "ListEnd"}));
	private double upperLimit;
	private boolean firstRun = true;
	private double index;
	private double inc;
	private List<String> commandToRun;
	private boolean nested = true;
	private String var;
	
	
	public For(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		checkIfEmpty(words);
		
		if(firstRun){
			firstRun = false;
			var = words.get(1);
			index = Double.parseDouble(words.get(2));
			getVariables().put(var, Double.toString(index));
			upperLimit = Double.parseDouble(words.get(3));
			inc = Double.parseDouble(words.get(4));
			if(checkBrackets(words,6)){
				commandToRun =  new ArrayList<String>();
				for(int i=7; i<words.size()-1; i++){
					commandToRun.add(words.get(i));
				}
			}
						
		}
		else{
			index+=inc;
			getVariables().put(var, Double.toString(index));
			nested = index<upperLimit;
		}

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
