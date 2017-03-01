package backend.states;

import java.util.Map;

/**
 * @author Tavo
 *
 */


public class State {
	
	private ActorModel actor;

	public State(State state){
			actor = new TurtleModel(state.getActor());
		
	}
	
	public State() {
		actor = new TurtleModel();
	}

	public ActorModel getActor() {
		return actor;
	}

	public void setActor(ActorModel actorModel) {
		this.actor = actorModel;
	}
	
	
	
}