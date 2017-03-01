package backend.states;

import javafx.collections.ObservableMap;

/**
 * @author Tavo
 *
 */


public class State {
	
	private ActorModel actor;
	private boolean clear;
	
	public State(State state){
			actor = new TurtleModel(state.getActor());
			clear = false;
	}
	
	public State() {
		actor = new TurtleModel();
		clear = false;
	}
	public ActorModel getActor() {
		return actor;
	}
	public void setActor(ActorModel actorModel) {
		this.actor = actorModel;
	}

	/**
	 * @return the clear
	 */
	public boolean clearscreen() {
		return clear;
	}

	/**
	 * @param clear the clear to set
	 */
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	
	
}
