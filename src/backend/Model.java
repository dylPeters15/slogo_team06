package backend;

import java.util.ResourceBundle;

import Exceptions.SlogoException;
import backend.interpreter.Interpreter;
import backend.states.*;
/**
 * @author Tavo Loaiza
 *
 */
public class Model {
	private StatesList<State> statesList;
	private Interpreter interpreter;
	
	public Model(){
		statesList = new StatesList<State>();
		statesList.add(new State());
		interpreter = new Interpreter(statesList);
		System.out.println("Starting turle pos: "+statesList.getLast().getActor().getPos());
	}
	
	public void interpret(String text) throws SlogoException{
		interpreter.interpret(text);
		System.out.println("Current Turtle pos: "+statesList.getLast().getActor().getPos());
		for(State s:statesList){
			System.out.println(" 	pos: "+s.getActor().getPos());
		}
	}
	
	public void setResourceBundle(String bundleName){
		interpreter.setLanguage(bundleName);
	}
	
}
