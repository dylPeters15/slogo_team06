package backend.interpreter.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	/**
	 * @param list
	 */
	public Command(StatesList<State> list){
		statesList = list;
	}
	
	/**
	 * @param cn
	 * @param statesList
	 * @return
	 * @throws SlogoException
	 */
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
			System.out.println(cn);
			throw new SlogoException("CommandDoesNotExist: ");
		}
		return comm;
	}	

	/**
	 * @return
	 * @throws SlogoException
	 */
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}
	
	/**
	 * @param a
	 * @return
	 * @throws SlogoException
	 */
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}
	/**
	 * @param a
	 * @param b
	 * @return
	 * @throws SlogoException
	 */
	public double runCommand(double a, double b) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 2");
	}
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 * @throws SlogoException
	 */
	public double runCommand(double a, double b, double c, double d) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 4");
	}

	/**
	 * @param words
	 * @return
	 * @throws SlogoException
	 */
	public double runCommand(List<String> words) throws SlogoException{
		throw new SlogoException("IncorrectParamType");
	}

	/**
	 * @param statesList
	 */
	public void setStatesList(StatesList<State> statesList) {
		this.statesList = statesList;
	}
	
	/**
	 * @return
	 */
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

	/**
	 * @return
	 */
	public abstract Integer numParamsNeeded();
	/**
	 * @return
	 */
	public List<String> paramsNeeded(){
		return new ArrayList<String>(Arrays.asList(new String []{}));
	}
	
	/**
	 * @return
	 */
	public boolean needsVarParams(){
		return DEF_NEEDS_VAR_PARAM;
	}
	
	/**
	 * @return
	 */
	public boolean needsCommandParams() {
		return DEF_NEEDS_COMMANDS_PARAM;
	}
	
	/**
	 * @return
	 */
	public boolean needsPriorCheck() {
		return DEF_NEEDS_PRIOR_CHECK;
    }
	
	/**
	 * @return
	 */
	public boolean isNestedCommand(){
		return DEF_REPEAT;
	}
	
	/**
	 * @return
	 */
	public List<String> nestedCommand(){
		return null;
	}
	
	/**
	 * @return
	 */
	public boolean ifDefineNewCommands(){
		return DEF_MAKE_NEW_COMMAND;
	}
	
	/**
	 * @param variables
	 */
	public void setVarMap(ObservableMap<String, String> variables){
		this.variables = variables;
	}
	
	/**
	 * @return the variables
	 */
	protected ObservableMap<String,String> getVariables() {
		return variables;
	}

	/**
	 * @return
	 */
	public String getVariablesString() {
		return null;
	}
	
	protected void setPenUp(boolean up) {
		State newState = getNewState();
		newState.getActors().setPenUp(up);
		addNewState(newState);
	}
	
	protected void addCommandToRun(List<String> commandToRun,List<String> words, int start) throws SlogoException {
		if(checkBrackets(words,start)){
			for(int i=start+1; i<words.size()-1; i++){
				commandToRun.add(words.get(i));
			}
		}
	}	

	protected void checkIfEmpty(List<String> words) throws SlogoException {
		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters: 0");
		}
	}	
	
	protected boolean checkBrackets(List<String> words, int i) throws SlogoException {
		boolean hasBrackets = words.get(i).contains("[") && words.get(words.size()-1).contains("]");
		if(!hasBrackets){
			throw new SlogoException("IncorrectNumOfBrackets");
		}
		return hasBrackets;
	}

}
