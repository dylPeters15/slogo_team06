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
	private String variables = "";
	
	public MakeUserInstruction(StatesList<State> list) {
		super(list);
	}
	
	@Override
	public double runCommand(List<String> words) throws SlogoException {
		if (words.isEmpty()) {
			throw new SlogoException("IncorrectNumOfParameters: 0");
		}
		// get the command name
		String commandName = "";
		String word = words.remove(0);
		while (!word.equals("[")) {
			commandName = commandName + word + " ";
			word = words.remove(0);
		}
		commandName = commandName.substring(0, commandName.length()-1); // remove the last blank
		// concatenate variables into a new string and return to interpreter
		word = words.remove(0);
		while (!word.equals("]")) {
			variables += "make ";
			variables = variables + word + " " + words.remove(0);
			word = words.remove(0);
		}
		// get the commands in the bracket
		String commands = "";
		word = words.remove(0);
		while (!word.equals("[")) {
			word = words.remove(0);
		}
		int numOfFrontBracket = 1;
		int numOfEndBracket = 0;
		while (!word.equals("]") || numOfFrontBracket != numOfEndBracket) {
			commands = commands + word + " ";
			if (!words.isEmpty()){
				word = words.remove(0);
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
	public String getVariablesString() {
		return variables;
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
