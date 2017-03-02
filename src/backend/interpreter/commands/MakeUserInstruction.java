package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.State;
import backend.states.StatesList;

public class MakeUserInstruction extends Command {
	
	private final int NUM_PARAMS = 3;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Variable", "Variable", "Command"}));

	public MakeUserInstruction(StatesList<State> list) {
		super(list);
	}

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
		throw new SlogoException("IncorrectNumOfParameters: 2");
	}
	
//	@Override
//	public double runCommand(List<String> words) throws SlogoException {
//		String commandName = "";
//		String word = words.get(0);
//		while (!type.getSymbol(word).equals("ListStart")) {
//			commandName = commandName + word + " ";
//			word = words.pop();
//		}
//		commandName = commandName.substring(0, commandName.length()); // remove the last blank
//		// TODO make new variables and put into variable map
//		String commands = "";
//		word = words.pop();
//		while (!type.getSymbol(word).equals("ListStart")) {
//			word = words.pop();
//		}
//		while (!type.getSymbol(word).equals("ListEnd")) {
//			commands = commands + word + " ";
//			word = words.pop();
//		}
//	}
	
	@Override
	public double runCommand(String commandName, String commands) throws SlogoException {
		try {
			getVariables().put(commandName, commands);
			return 1;
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public boolean ifDefineNewCommands(){
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
