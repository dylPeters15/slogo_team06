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
			parseText(text.split(WHITESPACE));
		}
		catch (SlogoException e){
			translateError(e);
			throw e;
		}
	}

	private void parseText (String[] text) throws SlogoException {	
		LinkedList<String> words = new LinkedList<String>(Arrays.asList(text));

		while(!words.isEmpty()){
			recursiveParse(words);
			System.out.println(variables);
		}

	}

	private double recursiveParse(LinkedList<String> words) throws SlogoException{

		if(words.isEmpty()){
			throw new SlogoException("IncorrectNumOfParameters");
		}

		String word = words.pop();

		if(isConstant(word)){
			return Double.parseDouble(word);
		}

		else if(type.getSymbol(word).equals("Command")){


			Command com = null;

			System.out.println(langParser.getSymbol(word));
			com = Command.getCommand(langParser.getSymbol(word), statesList);	
			try{

				if(com.needsVarParams()){
					com.setVarMap(variables);




					if(com.isNestedCommand()){
						List<String> params = new ArrayList<String>();

						for(int i=0; i<com.paramsNeeded().size(); i++){	
							getConstant(words, word, com, params, i);						
						}

						System.out.println(params.toString());


						double lastReturn = 0;
						while(com.isNestedCommand()){
							com.runCommand(params);	
							LinkedList<String> nestedCommand = new LinkedList<String>(com.nestedCommand());
							while(!nestedCommand.isEmpty()){
								System.out.println("NestedCommand = " + nestedCommand.toString());
								lastReturn = recursiveParse(nestedCommand);
							}
						}
						
						
						
						return lastReturn;
					}
					else{
						List<String> params = new ArrayList<String>();
						for(int i=0; i<com.numParamsNeeded(); i++){
							getConstant(words, word, com, params, i);
						}

						return com.runCommand(params);
					}
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
		if(type.getSymbol(word).equals("Variable")){
			try{
				return Double.parseDouble(variables.get(word.substring(1)));
			}
			catch (Exception e){
				throw new SlogoException("IncorrectParamType");
			}
		}
		else{
		}

		throw new SlogoException("IncorrectNumOfParameters");

	}
	private void getConstant(LinkedList<String> words, String word, Command com, List<String> params, int i)
			throws SlogoException {
		word = words.pop();		
		if(com.paramsNeeded().get(i).equals("Commands") && com.paramsNeeded().get(i+1).equals("ListEnd")){
			while(!type.getSymbol(word).equals("ListEnd") && !words.isEmpty() ){
				params.add(word);
				word = words.pop();
			}
			words.addFirst(word);
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

	public void setLanguage(String lang) {
		resources = ResourceBundle.getBundle(DEF_LANG);
		setParserPatterns(lang);
	}




}
