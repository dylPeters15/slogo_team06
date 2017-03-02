package backend;

import Exceptions.SlogoException;
import backend.interpreter.Interpreter;
import backend.states.*;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * @author Tavo Loaiza
 *
 */
public class Model {
	private StatesList<State> statesList;
	private ObservableMap<String,String> variables;
	
	public StatesList<State> getStatesList() {
		 return statesList;
	}

	private Interpreter interpreter;
	
	public Model(){
		variables = new SimpleMapProperty<String, String>(FXCollections.observableHashMap());
		statesList = new StatesList<State>();
		statesList.add(new State());
		interpreter = new Interpreter(statesList,variables);
		System.out.println("Starting Turtle pos: "+statesList.getLast().getActor().getPos());
		System.out.println("Starting Turtle heading: "+statesList.getLast().getActor().getHeading());
	}
	
	public void interpret(String text) throws SlogoException{
		interpreter.interpret(text);
		System.out.println("Current Turtle pos: "+statesList.getLast().getActor().getPos());
		System.out.println("Current Turtle heading: "+statesList.getLast().getActor().getHeading());
		for(State s:statesList){
			System.out.println(" 	pos: "+s.getActor().getPos());
		}
	}
	
	public void setResourceBundle(String bundleName){
		interpreter.setLanguage(bundleName);
	}
	
	public ObservableMap<String,String> getVariables(){
		return variables;
	}
	
}
