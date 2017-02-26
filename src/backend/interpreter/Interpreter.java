package backend.interpreter;
import java.util.Queue;

import backend.states.*;

public class Interpreter {
	private Queue<State> statesList;
	private final String DEF_LANG =  "resources/languages/English";
	private final String SYNTAX =  "resources/languages/Syntax";
	public final String WHITESPACE = "\\s+";
	private ProgramParser lang;
	
	public Interpreter(Queue<State> statesList){
		this.statesList = statesList;
		 lang = new ProgramParser();
		// these are more specific, so add them first to ensure they are checked first
		 lang.addPatterns(DEF_LANG);
	    // these are more general so add them at the end
	     lang.addPatterns(SYNTAX);
	}
	
	public void interpret(String text){		
		parseText(text.split(WHITESPACE));      
	}
	
	public void splitA(){        
        
	}
	
	private void parseText (String[] text) {
        for (String s : text) {
            if (s.trim().length() > 0) {            	
                System.out.println(String.format("%s : %s", s, lang.getSymbol(s)));                
                
            }
        }
        System.out.println();
    }
	
	
}
