package backend.states;

/**
 * @author Tavo
 *
 */


public class State {
	
	private ActorModel actor;
	
	public State() {
		// TODO all TurtleModel
		actor = new TurtleModel();
	}
	
	public State(double x, double y, double heading) {
		actor = new TurtleModel(x, y, heading);
	}

	public ActorModel getActor() {
		return actor;
	}

	public void setActor(ActorModel actorModel) {
		this.actor = actorModel;
	}
	
	
	
}
