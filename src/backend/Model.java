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
	}
	
	public void interpret(String text) throws SlogoException{
		interpreter.interpret(text);	
		System.out.println("All turtles: "+statesList.getLast().getActorMap().keySet());
		System.out.println("Active turtles: "+statesList.getLast().getActiveList());
		for(int id:statesList.getLast().getActiveList()){
			ActorModel a = statesList.getLast().getActorMap().get(id);
			System.out.println("	Id= "+id+" pos= "+a.getPos().toString()+ " heading = " + a.getHeading());
		}
	}
	
	public void setResourceBundle(String bundleName){
		interpreter.setLanguage(bundleName);
	}
	
	public ObservableMap<String,String> getVariables(){
		return variables;
	}
	
}
