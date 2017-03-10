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

	/**
	 * @param list
	 */
	public MakeVariable(StatesList<State> list) {
		super(list);
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(java.util.List)
	 */
	@Override
	public double runCommand(List<String> words) throws SlogoException{
	
		if(words.size()==NUM_PARAMS)
		{
			String var = words.get(0);
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
