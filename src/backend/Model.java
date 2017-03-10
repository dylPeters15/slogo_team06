package backend;

import java.util.ArrayList;
import java.util.List;

import Exceptions.SlogoException;
import backend.interpreter.Interpreter;
import backend.states.*;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * The class to connect with the front end.
 * Includes external APIs for the back end.
 * Depends on StatesList, Interpreter classes.
 * @author Tavo Loaiza
 *
 */
public class Model {
	private StatesList<State> statesList;
	private ObservableMap<String,String> variables;
	private List<String> pastCommands;
	private Interpreter interpreter;
	
	/**
	 * Constructor of the Model class.
	 */
	public Model(){
		variables = new SimpleMapProperty<String, String>(FXCollections.observableHashMap());
		statesList = new StatesList<State>();
		statesList.add(new State());
		interpreter = new Interpreter(statesList,variables);
		pastCommands = new ArrayList<String>();
	}
	
	/**
	 * The public API method for the front end to call.
	 * Interpret a user input sentence.
	 * @param text: the input sentence in String form
	 * @throws SlogoException
	 */
	public void interpret(String text) throws SlogoException{
		interpreter.interpret(text);	
		pastCommands.add(text);
		for(int id:statesList.getLast().getActiveList()){
			ActorModel a = statesList.getLast().getActorMap().get(id);
		}
	}
	
	/**
	 * The public API method for the front end to call.
	 * Get the current statesList.
	 * @return
	 */
	public StatesList<State> getStatesList() {
		 return statesList;
	}	

	/**
	 * The public API method for the front end to call.
	 * Get the current variables map.
	 * @return
	 */
	public ObservableMap<String,String> getVariables(){
		return variables;
	}
	
	/**
	 * The public API method for the front end to call.
	 * Get the current resource bundle when it is changed.
	 * @param bundleName
	 */
	public void setResourceBundle(String bundleName){
		interpreter.setLanguage(bundleName);
	}
	
	/**
	 * The public API method for the front end to call.
	 * Get the list of user input history.
	 * @return
	 */
	public List<String> getPastCommands(){
		return pastCommands;
	}
	
	/**
	 * The public API method for the front end to call.
	 * Set the list of user input history.
	 * @param list
	 */
	public void setPastCommands(List<String> list){
		pastCommands = list;
	}
	
}
