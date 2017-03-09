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
 * 
 * The class to interpret user input commands.
 * @author Tavo Loaiza
 *
 */
public class Interpreter {

	private StatesList<State> statesList;
	private final String DEF_LANG =  "resources/languages/English";
	private final String SYNTAX =  "resources/languages/Syntax";
	public final String WHITESPACE = "\\s+";
	private ProgramParser langParser;
	private ProgramParser type;
	private ResourceBundle resources;
	private ObservableMap<String,String> variables;

	/**
	 * Constructor of the Interpreter class.
	 * Takes the statesList and variablesList from the model.
	 * Get called from the Model class.
	 * @param statesList
	 * @param variables
	 */
	public Interpreter(StatesList<State> statesList, ObservableMap<String, String> variables){
		this.variables = variables;
		this.statesList = statesList;
		langParser = new ProgramParser();
		type = new ProgramParser();
		setLanguage(DEF_LANG);
	}		

	/**
	 * Method to set display language.
	 * @param lang
	 */
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

	/**
	 * The main method to interpret user input commands
	 * @param text: the user input commands in String form
	 * @throws SlogoException
	 */
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

	/**
	 * Separate an array of Strings into a LinkedList of words by blanks
	 * @param text: an array of Strings
	 * @return
	 * @throws SlogoException
	 */
	private LinkedList<String> separateWords (String[] text) throws SlogoException {	
		return new LinkedList<String>(Arrays.asList(text));
	}

	/**
	 * The method to parse inputs.
	 * The method stops until all input words are parsed.
	 * @param words
	 * @return
	 * @throws SlogoException
	 */
	private double parse(LinkedList<String> words) throws SlogoException {
		double result = 0;
		while (!words.isEmpty()) {
			result = recursiveParse(words);
		}
		return result;
	}

	/**
	 * The method to parse a group in inputs.
	 * Can be called for several times to parse different parts of an input.
	 * @param words
	 * @return
	 * @throws SlogoException
	 */
	private double recursiveParse(LinkedList<String> words) throws SlogoException{
		checkIfEmpty(words);
		String word = words.pop();  // get the first word to determine input type
		if(langParser.getSymbol(word).equals("Constant")){  // a constant
			return Double.parseDouble(word);
		}
		else if(type.getSymbol(word).equals("Command")){  // a pre-defined command
			return handleCommand(words, word);
		}
		else if(type.getSymbol(word).equals("Variable")){
			try{ // a variable
				return Double.parseDouble(variables.get(word.substring(1)));
			}
			catch (Exception e){  // a user-defined command
				interpretUserCommand(word);
			}
		}
		else if (type.getSymbol(word).equals("ListStart")) { 
			return parse(extractWordsInList(words));
		}
		throw new SlogoException("IncorrectNumOfParameters");
	}
	

	private void checkIfEmpty(LinkedList<String> words) throws SlogoException {
		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters");
		}
	}
	
	private double handleCommand(LinkedList<String> words, String word) throws SlogoException{
		Command com = null;
		try{
			// generate a pre-defined command (not user-defined)
			com = Command.getCommand(langParser.getSymbol(word), statesList);	
		}
		catch (SlogoException e){
			if(!variables.containsKey(word)){
				throw e;
			}
			// run the user-defined command
			return parse(separateWords(variables.get(word).split(WHITESPACE)));
		}
		try{ // run the pre-defined command
			return handleInstCommand(com, words, word);
		}
		catch (SlogoException e){
			e.setText(e.getText()+"->"+word);
			throw e;
		}
	}
	
	private double handleInstCommand(Command com, LinkedList<String> words, String word) throws SlogoException{
		if(com.needsVarParams()){  // make variables
			return handleVarParam(com, words, word);
		}
		else if (com.needsPriorCheck()) { // if and if-else
			return handleIfElse(words,com, word);
		}
		else if (com.ifDefineNewCommands()) { // TO
			com.setVarMap(variables);
			double result = com.runCommand(words);
			interpret(com.getVariablesString());
			return result;
		}
		else{	// normal commands				
			return handleNormalCommand(com, words);
		}
	}
	
	private double handleVarParam(Command com, LinkedList<String> words, String word) throws SlogoException{
		com.setVarMap(variables);
		if(com.isNestedCommand()){
			return handleNestedCommand(words, word, com);
		}
		else{
			return com.runCommand(getParams(words, word, com));
		}
	}
	
	private double handleIfElse(LinkedList<String> words, Command com, String word) throws SlogoException{
		double condition = recursiveParse(words);
		if (condition != 0) {
			if(com.numParamsNeeded() == 2){ // if
				return com.runCommand(condition,recursiveParse(words));
			}
			else if (com.numParamsNeeded() == 3){ // else if
				double executedStatement = recursiveParse(words);
				removeElse(words, word);
				return com.runCommand(condition, executedStatement);
			}
			throw new SlogoException("CommandDoesNotExist");
		}
		else {
			if(com.numParamsNeeded() == 2){ // if
				return removeIf(words, word);
			}
			else if (com.numParamsNeeded() == 3) { // else if
				removeElse(words, word);
				double executedStatement = recursiveParse(words);
				return com.runCommand(condition, executedStatement);
			}
			throw new SlogoException("CommandDoesNotExist");
		}
	}
	
	private double removeIf(LinkedList<String> words, String word){
		// remove the if statement
		word = words.pop();
		while (!type.getSymbol(word).equals("ListEnd")) {
			word = words.pop();
		}
		return 0;
	}

	private void removeElse(LinkedList<String> words, String word) throws SlogoException {
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
	}
	
	private double handleNormalCommand(Command com, LinkedList<String> words) throws SlogoException{
		if(com.numParamsNeeded() == 1){
			return com.runCommand(recursiveParse(words));
		}
		else if(com.numParamsNeeded() == 2){
			return com.runCommand(recursiveParse(words),recursiveParse(words));
		}
		else if (com.numParamsNeeded() == 4) {
			return com.runCommand(recursiveParse(words),recursiveParse(words), recursiveParse(words), recursiveParse(words));
		}
		return com.runCommand();
	}
	
	private void interpretUserCommand(String word) throws SlogoException {
		String command = variables.get(word.substring(1));
		try{
			interpret(command);
		}
		catch (Exception event){
			throw new SlogoException("VariableDoesNotExist");
		}
	}

	private List<String> getParams(LinkedList<String> words, String word, Command com) throws SlogoException {
		List<String> params = new ArrayList<String>();
		for(int i=0; i<com.numParamsNeeded(); i++){
			getConstant(words, word, com, params, i);
		}
		return params;
	}

	private LinkedList<String> extractWordsInList(LinkedList<String> words) throws SlogoException {
		String word;
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
		return bracketWords;
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
		int bracket = 1;
		while(!(type.getSymbol(word).equals("ListEnd") && bracket==0) && !words.isEmpty() ){
			params.add(word);
			
			word = words.pop();
			if(type.getSymbol(word).equals("ListStart")){
				bracket++;
			}
			if(type.getSymbol(word).equals("ListEnd")){
				bracket--;
			}
		}
		words.addFirst(word);
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
