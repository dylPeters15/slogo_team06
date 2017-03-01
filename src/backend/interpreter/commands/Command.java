package backend.interpreter.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import backend.states.*;


public abstract class Command {

	private StatesList<State> statesList;

	public Command(StatesList<State> list){
		statesList = list;
	}

	public abstract double runCommand();
	public abstract double runCommand(double a);
	public abstract double runCommand(double a, double b);
	
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

	abstract Integer numParamsNeeded();
	abstract List<String> paramsNeeded();

	public static Command getCommand(String commandName, StatesList<State> statesList) throws ClassNotFoundException {
		Command comm;
		try {
			Class<?> myClass = Class.forName("backend.interpreter.commands." + commandName);
			@SuppressWarnings("unchecked")
			Constructor<Command> constructor = (Constructor<Command>) myClass.getConstructor(StatesList.class);
			comm = (Command) constructor.newInstance(statesList);
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException | NoSuchMethodException
				| InstantiationException | IllegalAccessException | InvocationTargetException e) {
			System.err.println("Could not find command of the name: "+commandName);
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
		return comm;
	}	


}
