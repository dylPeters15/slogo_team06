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
	private ObservableMap<String,String> variables;
	
	
	public Command(StatesList<State> list){
		statesList = list;
	}

	public abstract double runCommand() throws SlogoException;
	public abstract double runCommand(double a) throws SlogoException;
	public abstract double runCommand(double a, double b) throws SlogoException;
	public double runCommand(String commandName, String commands) throws SlogoException{
		throw new SlogoException("IncorrectParamType");
	}

	public double runCommand(List<String> words) throws SlogoException{
		throw new SlogoException("IncorrectParamType");
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
	
	protected void removeNewState() {
		statesList.removeLast();
	}
	
	public void setStatesList(StatesList<State> statesList) {
		this.statesList = statesList;
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
	
	public boolean ifDefineNewCommands(){
		return DEF_MAKE_NEW_COMMAND;
	}
	
	public void setVarMap(ObservableMap<String, String> variables){
		this.variables = variables;
	}

	public static Command getCommand(String commandName, StatesList<State> statesList) throws SlogoException {
		Command comm;
		try {
			Class<?> myClass = Class.forName("backend.interpreter.commands." + commandName);
			@SuppressWarnings("unchecked")
			Constructor<Command> constructor = (Constructor<Command>) myClass.getConstructor(StatesList.class);
			comm = (Command) constructor.newInstance(statesList);
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException | NoSuchMethodException
				| InstantiationException | IllegalAccessException | InvocationTargetException e) {
			
			throw new SlogoException("CommandDoesNotExist:commandName");
		}
		return comm;
	}	
	
	/**
	 * @return the variables
	 */
	protected ObservableMap<String,String> getVariables() {
		return variables;
	}
	/**
	 * @param variables the variables to set
	 */
	protected void setVariables(ObservableMap<String,String> variables) {
		this.variables = variables;
	}

}
