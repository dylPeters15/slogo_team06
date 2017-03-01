package backend.interpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import Exceptions.SlogoException;
import backend.interpreter.commands.Command;
import backend.interpreter.commands.Forward;
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
		
		System.out.println(recursiveParse(words));
		
		
		
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
					List<String> params = new ArrayList<String>();
					for(int i=0; i<com.numParamsNeeded(); i++){
						word = words.pop();
						if(com.paramsNeeded().get(i).equals(type.getSymbol(word))){
							params.add(word);
						}
						else if(isConstant(word)){
								params.add(Double.toString(recursiveParse(words)));							
						}
					}
					
					return com.runCommand(params);
		
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
				throw new SlogoException("IncorrectParamType");
			}
		}
		
		throw new SlogoException("IncorrectNumOfParameters");
		
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
