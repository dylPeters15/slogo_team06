package backend.interpreter.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import Exceptions.SlogoException;
import backend.states.*;
import javafx.collections.ObservableMap;

/**
 * @author Tavo Loaiza
 *
 */
public abstract class Command {

	private StatesList<State> statesList;
	private boolean DEF_NEEDS_VAR_PARAM = false;
	private boolean DEF_NEEDS_COMMANDS_PARAM = false;
	private boolean DEF_NEEDS_PRIOR_CHECK = false;
	private boolean DEF_MAKE_NEW_COMMAND = false;
	private boolean DEF_REPEAT = false;
	private ObservableMap<String,String> variables;
	
	
	public Command(StatesList<State> list){
		statesList = list;
	}
	
	public static Command getCommand(String cn, StatesList<State> statesList) throws SlogoException {
		Command comm;
		try {
			Class<?> myClass = Class.forName("backend.interpreter.commands." + cn);
			@SuppressWarnings("unchecked")
			Constructor<Command> constructor = (Constructor<Command>) myClass.getConstructor(StatesList.class);
			comm = (Command) constructor.newInstance(statesList);
		} 
		catch (ClassNotFoundException | IllegalArgumentException | SecurityException | NoSuchMethodException
				| InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new SlogoException("CommandDoesNotExist:commandName");
		}
		return comm;
	}	

	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters");
	}
	
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters");
	}
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters");
	}
	
	public double runCommand(double a, double b, double c, double d) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters");
	}

	public double runCommand(List<String> words) throws SlogoException{
		throw new SlogoException("IncorrectParamType");
	}

	public void setStatesList(StatesList<State> statesList) {
		this.statesList = statesList;
	}
	
	public StatesList<State> getStatesList() {
		return statesList;
	}
	
	protected State getLastState() {
		return getStatesList().getLast();
	}

	protected State getNewState(){
		return new State(getLastState());
	}
	
	protected void addNewState(State state){
		statesList.add(state);
	}	

	public abstract Integer numParamsNeeded();
	public abstract List<String> paramsNeeded();
	
	public boolean needsVarParams(){
		return DEF_NEEDS_VAR_PARAM;
	}
	
	public boolean needsCommandParams() {
		return DEF_NEEDS_COMMANDS_PARAM;
	}
	
	public boolean needsPriorCheck() {
		return DEF_NEEDS_PRIOR_CHECK;
    }
	
	public boolean isNestedCommand(){
		return DEF_REPEAT;
	}
	
	public List<String> nestedCommand(){
		return null;
	}
	
	public boolean ifDefineNewCommands(){
		return DEF_MAKE_NEW_COMMAND;
	}
	
	public void setVarMap(ObservableMap<String, String> variables){
		this.variables = variables;
	}
	
	/**
	 * @return the variables
	 */
	protected ObservableMap<String,String> getVariables() {
		return variables;
	}

	public String getVariablesString() {
		return null;
	}

}
