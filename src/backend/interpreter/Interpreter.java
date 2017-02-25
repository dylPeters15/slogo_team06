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
		
		System.out.println("I got text!");
        // try against different inputs
        String userInput = "fd 50 rt 90 BACK :distance Left :angle";
        
        
        //lang.getSymbol(userInput);
	}
	
	public void test(){
        
        
        
	}
	
	
}
