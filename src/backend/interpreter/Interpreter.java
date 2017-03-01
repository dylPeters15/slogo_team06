package backend.interpreter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import Exceptions.SlogoException;
import backend.interpreter.commands.Command;
import backend.interpreter.commands.Forward;
import backend.states.*;
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
	
	
	public Interpreter(StatesList<State> statesList){
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
		
		recursiveParse(words);
		
		
		
        for (String s : text) {
            if (s.trim().length() > 0) {            	
             //   System.out.println(String.format("%s : %s", s, lang.getSymbol(s)));            
            }
        }
        System.out.println();
    }
	
	private double recursiveParse(LinkedList<String> words) throws SlogoException{
				
		if(words.isEmpty()){
				throw new SlogoException("IncorrectNumOfParameters");
			}
		
		String word = words.pop();
		
		if(langParser.getSymbol(word).equals("Constant")){
			return Double.parseDouble(word);
		}
		else if(type.getSymbol(word).equals("Command")){
			Command com = null;
			
				System.out.println(langParser.getSymbol(word));
				com = Command.getCommand(langParser.getSymbol(word), statesList);	
			try{
				
				if(com.numParamsNeeded() == 1){
					return com.runCommand(recursiveParse(words));
				}
				if(com.numParamsNeeded() == 2){
					return com.runCommand(recursiveParse(words),recursiveParse(words));
				}
				else 
					return com.runCommand();
			}
			catch (SlogoException e){
				e.setText(e.getText()+"->"+word);
			}
		}
		
		throw new SlogoException("IncorrectNumOfParameters");
		
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
