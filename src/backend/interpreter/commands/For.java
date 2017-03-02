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
	
		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters: 0");
		}
		
		if(firstRun){
			firstRun = false;
			var = words.get(1);
			index = Double.parseDouble(words.get(2));
			getVariables().put(var, Double.toString(index));
			upperLimit = Double.parseDouble(words.get(3));
			inc = Double.parseDouble(words.get(4));
			if(words.get(6).contains("[") && words.get(words.size()-1).contains("]")){
				commandToRun =  new ArrayList<String>();
				for(int i=7; i<words.size()-1; i++){
					commandToRun.add(words.get(i));
				}
			}
			else{
				
				throw new SlogoException("IncorrectNumOfBrackets");
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
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
