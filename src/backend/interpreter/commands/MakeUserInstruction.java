package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
	
	@Override
	public double runCommand(List<String> words) throws SlogoException {
		LinkedList<String> newWords = new LinkedList<String>(words);
		String commandName = "";
		String word = newWords.pop();
		while (!word.equals("[")) {
			commandName = commandName + word + " ";
			word = newWords.pop();
		}
		commandName = commandName.substring(0, commandName.length()-1); // remove the last blank
		// make new variables and put into variable map
		word = newWords.pop();
		while (!word.equals("]")) {
			this.getVariables().put(word, newWords.pop());
			word = newWords.pop();
		}					
		String commands = "";
		word = newWords.pop();
		while (!word.equals("[")) {
			word = newWords.pop();
		}
		int numOfFrontBracket = 1;
		int numOfEndBracket = 0;
		while (!word.equals("]") || numOfFrontBracket != numOfEndBracket) {
			commands = commands + word + " ";
			if (!words.isEmpty()){
				word = newWords.pop();
			}
			else {
				throw new SlogoException("ExceptedBracket");
			}
			if (word.equals("[")) numOfFrontBracket ++;
			if (word.equals("]")) numOfEndBracket ++;
		}
		commands = commands + "]";
		try {
			getVariables().put(commandName, commands);
			return 1;
		}
		catch (Exception e) {
			return 0;
		}
	}
	
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
