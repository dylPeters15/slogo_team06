package backend.states;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Tavo Loaiza
 *Composite class to update a list of ActorModels while behaving as a single ActorModel
 */
public class ActorCompositeModel{
	
	private Map<Integer,ActorModel> actorMap;
	private List<Integer> active;
	
	/**Constructor for ActorCompositeModel
	 * @param actorMap with an ID as the key, and an ActorModel as a val
	 * @param activeList List of active actor IDs.
	 */
	ActorCompositeModel(Map<Integer,ActorModel> actorMap, List<Integer> activeList){	
		set(actorMap, activeList);	
	}

	/**Set the containing ActorModel Map, and the list of active actors
	 * @param actorMap with an ID as the key, and an ActorModel as a val
	 * @param activeList List of active actor IDs.
	 */
	public void set(Map<Integer, ActorModel> actorMap, List<Integer> activeList) {
		this.actorMap = actorMap;
		active = activeList;
	}

	private Collection<ActorModel> actors(){
		List<ActorModel> actors = new ArrayList<ActorModel>();
		for(int id:active){
			actors.add(actorMap.get(id));		
		}
		return actors;
	}
	private ActorModel lastActor(){
		return actorMap.get(active.get(active.size()-1));
	}
	
	/**Move all the active actors forward a given distance
	 * @param distance to move forward
	 */
	public void moveForward(double distance){
		actors().forEach((actor)-> actor.moveForward(distance));
	}
	
	/**Move all the active actors backward a given distance
	 * @param distance
	 */
	public void moveBackward(double distance){
		actors().forEach((actor)->actor.moveForward(-distance));
	}
	
	/**Set's all the active actors visibility
	 * @param v, where true will make the active actors visible
	 */
	public void setVisible(boolean v) {
		actors().forEach((actor)->actor.setVisible(v));
	}

	/**Returns if pen is up or down
	 * @return if pen is up or down
	 */
	public boolean getPenUp() {	
		return lastActor().getPenUp();
	}
	
	/**Returns position of the last active ActorModel
	 * @return  position of the last active ActorModel
	 */
	public Point2D.Double getPos() {	
		return lastActor().getPos();
	}
	/**Set's the active actors to a given position
	 * @param pos new position of the active actor
	 */
	public void setPos(Point2D.Double pos) {
		actors().forEach((actor)->actor.setPos(pos));
	}
	/**Returns the heading of the last active ActorModel
	 * @return
	 */
	public double getHeading() {
		return lastActor().getHeading();
	}
	/**Set's the heading of all the active actors
	 * @param heading to set all the active actors
	 */
	public void setHeading(Double heading) {
		actors().forEach((actor)->actor.setHeading(heading));
	}
	/**Set all the active actors pen up or down
	 * @param penUp If true, active ActorModel up
	 */
	public void setPenUp(boolean penUp) {
		actors().forEach((actor)->actor.setPenUp(penUp));
	}

	/**Returns if the last active actor is visible
	 * @return if the last active actor is visible
	 */
	public boolean getVisible() {
		return lastActor().getVisible();
	}

	/**Rotates the heading of all the active actors a given distance
	 * @param distance to rotate
	 */
	public void rotate(double distance) {
		actors().forEach((actor)->actor.setHeading(actor.getHeading()+distance));
	}
	
	/**Set's the heading of all the active actors toward a point.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void setTowards(double x, double y) {
		actors().forEach((actor)->setToward(actor,x,y));
	}
	
	private void setToward(ActorModel actor, double a, double b){
		double newAngle = Math.toDegrees(Math.atan(actor.getPos().getY() / actor.getPos().getX()));
		actor.setHeading(newAngle);
	}

	/**Returns the ID of the last active turtle
	 * @return the ID of the last active turtle
	 */
	public double getId() {
		return active.get(active.size()-1);
	}
	
}