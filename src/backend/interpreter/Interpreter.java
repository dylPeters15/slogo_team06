package backend.interpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import Exceptions.SlogoException;
import backend.interpreter.commands.Command;
import backend.states.*;
import javafx.collections.ObservableMap;
/**
 * @author Tavo Loaiza
 *
 */
public class Interpreter {
	
	private StatesList<State> statesList;
	private final String DEF_LANG =  "resources/languages/English";
	private final String SYNTAX =  "resources/languages/Syntax";
	public final String WHITESPACE = "\\s+";
	private final String classPrefix = "backend.interpreter.commands.";
	private ProgramParser langParser;
	private ProgramParser type;
	ResourceBundle resources;
	private ObservableMap<String,String> variables;

	public Interpreter(StatesList<State> statesList, ObservableMap<String, String> variables){
		this.variables = variables;
		this.statesList = statesList;
		langParser = new ProgramParser();
		type = new ProgramParser();
		setLanguage(DEF_LANG);
	}		

	public void setLanguage(String lang) {
		resources = ResourceBundle.getBundle(DEF_LANG);
		setParserPatterns(lang);
	}
	
	private void setParserPatterns(String lang) {
		langParser.clearPatterns();
		type.clearPatterns();
		// these are more specific, so add them first to ensure they are checked first
		langParser.addPatterns(lang);
		// these are more general so add them at the end
		langParser.addPatterns(SYNTAX);
		type.addPatterns(SYNTAX);
	}

	public void interpret(String text) throws SlogoException{
		try{
			LinkedList<String> words = separateWords(text.split(WHITESPACE));
			parse(words);
		}
		catch (SlogoException e){
			translateError(e);
			throw e;
		}
	}

	private LinkedList<String> separateWords (String[] text) throws SlogoException {	
		return new LinkedList<String>(Arrays.asList(text));
	}

	private double parse(LinkedList<String> words) throws SlogoException {
		double result = 0;
		while (!words.isEmpty()) {
			result = recursiveParse(words);
		}
		return result;
	}

	private double recursiveParse(LinkedList<String> words) throws SlogoException{
		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters");
		}
		// the first word in input, used to indicate input type
		String word = words.pop();

		if(isConstant(word)){
			return Double.parseDouble(word);
		}
		else if(type.getSymbol(word).equals("Command")){
			Command com = null;
			try{
				// generate a predefined command (not user-defined)
				com = Command.getCommand(langParser.getSymbol(word), statesList);	
			}
			catch (SlogoException e){
				if(!variables.containsKey(word)){
					throw e;
				}
				else{
					// run the user-defined command
					return parse(separateWords(variables.get(word).split(WHITESPACE)));
				}
			}
			try{
				if(com.needsVarParams()){  // make variables
					com.setVarMap(variables);
					if(com.isNestedCommand()){
						return handleNestedCommand(words, word, com);
					}
					else{
						List<String> params = new ArrayList<String>();
						for(int i=0; i<com.numParamsNeeded(); i++){
							getConstant(words, word, com, params, i);
						}
						return com.runCommand(params);
					}
				}
				else if (com.needsPriorCheck()) { // if and if-else
					double condition = recursiveParse(words);
					if (condition != 0) {
						if(com.numParamsNeeded() == 2){ // if
							return com.runCommand(condition,recursiveParse(words));
						}
						else if (com.numParamsNeeded() == 3){ // else if
							double executedStatement = recursiveParse(words);
							// remove the else statements
							if (!words.isEmpty()) {
								word = words.pop();
								while (!type.getSymbol(word).equals("ListEnd")) {
									word = words.pop();
								}
							}
							else {
								throw new SlogoException("IncorrectNumOfParameters");
							}
							return com.runCommand(condition, executedStatement);
						}
						else {
							throw new SlogoException("CommandDoesNotExist");
						}
					}
					else {
						if(com.numParamsNeeded() == 2){ // if
							// remove the if statement
							word = words.pop();
							while (!type.getSymbol(word).equals("ListEnd")) {
								word = words.pop();
							}
							return 0;
						}
						else if (com.numParamsNeeded() == 3) { // else if
							// remove the if statement
							if (!words.isEmpty()) {
								word = words.pop();
								while (!type.getSymbol(word).equals("ListEnd")) {
									word = words.pop();
								}
							}
							else {
								throw new SlogoException("IncorrectNumOfParameters");
							}
							double executedStatement = recursiveParse(words);
							return com.runCommand(condition, executedStatement);
						}
						else {
							throw new SlogoException("CommandDoesNotExist");
						}
					}

				}
				else if (com.ifDefineNewCommands()) { // TO
					com.setVarMap(variables);
					double result = com.runCommand(words);
					interpret(com.getVariablesString());
					return result;
				}
				else{
					if(com.numParamsNeeded() == 1){
						return com.runCommand(recursiveParse(words));
					}
					if(com.numParamsNeeded() == 2){
						return com.runCommand(recursiveParse(words),recursiveParse(words));
					}
					else {
						return com.runCommand();
					}
				}
			}
			catch (SlogoException e){
				e.setText(e.getText()+"->"+word);
			}
		}
		else if(type.getSymbol(word).equals("Variable")){
			try{
				return Double.parseDouble(variables.get(word.substring(1)));
			}
			catch (Exception e){
//				throw new SlogoException("IncorrectParamType");
			}
			finally {
				// not a double, then a command name
				String command = variables.get(word.substring(1));
				interpret(command);
			}
		}
		else if (type.getSymbol(word).equals("ListStart")) { 
			// remove the brackets, and parse all inside words
			LinkedList<String> bracketWords = new LinkedList<>();
			word = words.pop();			
			int numOfFrontBracket = 1;
			int numOfEndBracket = 0;
			while (!type.getSymbol(word).equals("ListEnd") || numOfFrontBracket != numOfEndBracket) {
				bracketWords.add(word);
				if (!words.isEmpty()){
					word = words.pop();
				}
				else {
					throw new SlogoException("ExceptedBracket");
				}
				if (type.getSymbol(word).equals("ListStart")) numOfFrontBracket ++;
				if (type.getSymbol(word).equals("ListEnd")) numOfEndBracket ++;
			}
			return parse(bracketWords);
		}
		throw new SlogoException("IncorrectNumOfParameters");

	}

	private double handleNestedCommand(LinkedList<String> words, String word, Command com) throws SlogoException {
		List<String> params = new ArrayList<String>();

		for(int i=0; i<com.paramsNeeded().size(); i++){	
			getConstant(words, word, com, params, i);						
		}

		double lastReturn = 0;
		while(com.isNestedCommand()){
			com.runCommand(params);	
			LinkedList<String> nestedCommand = new LinkedList<String>(com.nestedCommand());
			while(!nestedCommand.isEmpty()){
				lastReturn = recursiveParse(nestedCommand);
			}

		}

		return lastReturn;
	}
	private void getConstant(LinkedList<String> words, String word, Command com, List<String> params, int i)
			throws SlogoException {
		word = words.pop();		
		if(checkAndGetList(com, i, words, word, params)){
			
		}
		else if(com.paramsNeeded().get(i).equals(type.getSymbol(word))){
			if(type.getSymbol(word).equals("Variable")){
				params.add(word.substring(1));
			}		
			else{
				params.add(word);
			}
		}
		else if(com.paramsNeeded().get(i).equals("Constant")){
			words.addFirst(word);
			params.add(Double.toString(recursiveParse(words)));	
		}
		else if( com.paramsNeeded().get(i).equals("ListStart") ||  com.paramsNeeded().get(i).equals("ListEnd")){
			throw new SlogoException("ExceptedBracket");
		}
	}

	private boolean checkAndGetList(Command com, int i, LinkedList<String> words, String word, List<String> params) {
		boolean isList =  ( com.paramsNeeded().get(i).equals("Commands") 
						|| com.paramsNeeded().get(i).equals("Constants"))
						&& com.paramsNeeded().get(i+1).equals("ListEnd");
		if(isList){
			getTillListEnd(words, word, params);
		}
		return isList;
	}

	private void getTillListEnd(LinkedList<String> words, String word, List<String> params) {
		while(!type.getSymbol(word).equals("ListEnd") && !words.isEmpty() ){
			params.add(word);
			word = words.pop();
		}
		words.addFirst(word);
	}
	
	private boolean isConstant(String word) {
		return langParser.getSymbol(word).equals("Constant");
	}
	
	public void translateError(SlogoException e){
		int indexOfCustomMessage = e.getText().indexOf(';');
		if(indexOfCustomMessage==-1){
			indexOfCustomMessage = e.getText().length();
		}
		String toReplace = e.getText().substring(0,indexOfCustomMessage);
		e.setText(e.getText().replaceAll(toReplace, resources.getString(toReplace)));
	}

}
