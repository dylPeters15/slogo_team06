package backend.interpreter.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.*;

/**
 * @author Tavo Loaiza
 *
 */
public abstract class Command {

	private StatesList<State> statesList;

	public Command(StatesList<State> list){
		statesList = list;
	}

	public abstract double runCommand() throws SlogoException;
	public abstract double runCommand(double a);
	public abstract double runCommand(double a, double b) throws SlogoException;
	
	public StatesList<State> getStatesList() {
		return statesList;
	}

	protected State getNewState(){
		return new State(getStatesList().getLast());
	}
	protected void addNewState(State state){
		statesList.add(state);
	}
	
	public void setStatesList(StatesList<State> statesList) {
		this.statesList = statesList;
	}

	public abstract Integer numParamsNeeded();
	public abstract List<String> paramsNeeded();

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


}
