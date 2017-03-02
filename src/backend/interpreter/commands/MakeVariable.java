package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class MakeVariable extends Command {
	
	private final int NUM_PARAMS = 2;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Variable", "Constant"}));

	public MakeVariable(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		if(words.size()==NUM_PARAMS)
		{
			String var = words.get(0).substring(1);
			Double val =  0.0;
			try{
				val = Double.parseDouble(words.get(1));
			}
			catch(Exception e){
				throw new SlogoException("IncorrectParamType");
			}			
			getVariables().put(var, val.toString());
			return val;
		}
		else{
			throw new SlogoException("IncorrectParamType");
		}
		
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