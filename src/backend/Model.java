package backend;

import backend.interpreter.Interpreter;
import backend.states.*;

public class Model {
	StatesList<State> statesList;
	Interpreter interpreter;
	
	
	public Model(){
		statesList = new StatesList<State>();
		interpreter = new Interpreter(statesList);
	}
	
	public void interpret(String text){
		interpreter.interpret(text);
	}
	
}
