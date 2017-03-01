package backend;

import backend.interpreter.Interpreter;
import backend.states.*;

public class Model {
	StatesList<State> statesList;
	Interpreter interpreter;
	
	
	public Model(){
		statesList = new StatesList<State>();
		statesList.add(new State());
		interpreter = new Interpreter(statesList);
		System.out.println("Starting turle pos: "+statesList.getLast().getActor().getPos());
	}
	
	public void interpret(String text){
		interpreter.interpret(text);
		System.out.println("Current Turtle pos: "+statesList.getLast().getActor().getPos());
		for(State s:statesList){
			System.out.println(" 	pos: "+s.getActor().getPos());
		}
	}
	
}
