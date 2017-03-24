package backend.interpreter.commands;
// This entire file is part of my masterpiece.
// Tavo Loaiza
/**
* This class is follows designed principle of hierarchy, as it is an abstract 
* class that is implemented by specific command classes. It severly reduces the
* duplicated code of the child classes by holding commonly used methods 
* (such as checking if the command parameters have the correct number of brackets).
* It also uses advanced java techniques to simply the code. 
* For example, it uses reflection to instanstiate instances of the class, which
* avoid relying on a huge block of if statements.
*/

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
	 * @return return value of the command
	 * @throws SlogoException
	 */
	public double runCommand() throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 0");
	}
	
	/**
	 * @param a
	 * @return return value of the command
	 * @throws SlogoException
	 */
	public double runCommand(double a) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 1");
	}
	/**
	 * @param a
	 * @param b
	 * @return return value of the command
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
	 * @return return value of the command
	 * @throws SlogoException
	 */
	public double runCommand(double a, double b, double c, double d) throws SlogoException {
		throw new SlogoException("IncorrectNumOfParameters: 4");
	}

	/**
	 * @param words
	 * @return return value of the command
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
	
	protected StatesList<State> getStatesList() {
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
	 * @return The number of parameters needed
	 */
	public abstract Integer numParamsNeeded();
	/**
	 * @return a list with the type of parameters needed
	 */
	public List<String> paramsNeeded(){
		return new ArrayList<String>(Arrays.asList(new String []{}));
	}
	
	/**
	 * @return true if it requires looking up a variable parameter, false otherwise
	 */
	public boolean needsVarParams(){
		return DEF_NEEDS_VAR_PARAM;
	}
	
	/**
	 * @return true if the command needs a list of parameters, false otherwise
	 */
	public boolean needsCommandParams() {
		return DEF_NEEDS_COMMANDS_PARAM;
	}
	
	/**
	 * @return true if the command is a conditional, false otherwise
	 */
	public boolean needsPriorCheck() {
		return DEF_NEEDS_PRIOR_CHECK;
    }
	
	/**
	 * @return true if there is a nested command that must be run
	 */
	public boolean isNestedCommand(){
		return DEF_REPEAT;
	}
	
	/**
	 * @return the nested command to be run
	 */
	public List<String> nestedCommand(){
		return null;
	}
	
	/**
	 * @return if this command defines a user command
	 */
	public boolean ifDefineNewCommands(){
		return DEF_MAKE_NEW_COMMAND;
	}
	
	/**
	 * Sets the variable map of this command
	 * @param variables
	 */
	public void setVarMap(ObservableMap<String, String> variables){
		this.variables = variables;
	}
	
	/**
	 * @return the variable map from this command
	 */
	protected ObservableMap<String,String> getVariables() {
		return variables;
	}

	/**
	 * @return Variables as a string (for defining a new command)
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
