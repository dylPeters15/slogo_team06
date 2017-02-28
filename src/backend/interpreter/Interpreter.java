package backend.interpreter;
import java.util.LinkedList;
import java.util.Queue;

import backend.states.*;

public class Interpreter {
	private Queue<State> statesList; // add new states to the queue
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
		Queue<String> commands = parseText(text.split(WHITESPACE));                   
        // first assume commands are valid, create new states according to commands 
		generateStates(commands);
		System.out.println(String.format("Position: %s, Heading: %s, PenUp: %s, Visible: %s", 
				statesList.peek().getActor().getPos().toString(), 
				statesList.peek().getActor().getHeading().toString(),
				statesList.peek().getActor().getPenUp(),
				statesList.peek().getActor().getVisible()));
	}
	
	public void splitA(){        
        
	}
	
	private Queue<String> parseText (String[] text) {
		Queue<String> commands = new LinkedList<>();
        for (String s : text) {
            if (s.trim().length() > 0) { 
            	commands.add(s);
                System.out.println(String.format("%s : %s", s, lang.getSymbol(s)));                
            }
        }
        System.out.println();
        return commands;
    }
	
	private void generateStates(Queue<String> commands) {
		while (!commands.isEmpty()) {
			String command1 = commands.poll();
			if (lang.getSymbol(command1).equals("Forward")) {
				String command2 = commands.poll();
				if (lang.getSymbol(command2).equals("Constant")) {
					statesList.add(new State(0, Integer.parseInt(command2), 0));
				}
				else {
					// TODO error
				}
			}
			else if (lang.getSymbol(command1).equals("Backward")) {
				// TODO other commands
			}
		}
	}
}
