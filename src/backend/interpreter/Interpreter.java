package backend.interpreter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import backend.interpreter.commands.Command;
import backend.interpreter.commands.Forward;
import backend.states.*;

public class Interpreter {
	private StatesList<State> statesList;
	private final String DEF_LANG =  "resources/languages/English";
	private final String SYNTAX =  "resources/languages/Syntax";
	public final String WHITESPACE = "\\s+";
	private ProgramParser lang;
	private ProgramParser type;
	public Interpreter(StatesList<State> statesList){
		this.statesList = statesList;
		 lang = new ProgramParser();
		 type = new ProgramParser();
		 
		// these are more specific, so add them first to ensure they are checked first
		 lang.addPatterns(DEF_LANG);
	    // these are more general so add them at the end
	     lang.addPatterns(SYNTAX);
	     type.addPatterns(SYNTAX);
	}
	
	public void interpret(String text){
		parseText(text.split(WHITESPACE));
	}
	
	private void parseText (String[] text) {
		
		LinkedList<String> words = new LinkedList<String>(Arrays.asList(text));
		
		recursiveParse(words, null);
				
		
        for (String s : text) {
            if (s.trim().length() > 0) {            	
             //   System.out.println(String.format("%s : %s", s, lang.getSymbol(s))); 
            }
        }
        System.out.println();
    }
	private double recursiveParse(LinkedList<String> words, String lastWord){
		
		
		if(words.isEmpty()){
			if(lang.getSymbol(lastWord).equals("Constant")){
				return Double.parseDouble(lastWord);
			}
			else{
				System.err.println("Error, last parameter is not a constant");
				//Throw error;
			}
						
		}
		
		String word = words.pop();
		
		if(lang.getSymbol(word).equals("Constant")){
			return Double.parseDouble(word);
		}
		if(type.getSymbol(word).equals("Command")){
			Command com = null;
			try {
				System.out.println(lang.getSymbol(word));
				com = Command.getCommand(lang.getSymbol(word), statesList);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return com.runCommand(recursiveParse(words, word));
		}
		
		return recursiveParse(words, word);
	}

}
