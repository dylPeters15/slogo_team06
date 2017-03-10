package backend.states;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ActorCompositeModel{
	
	private Map<Integer,ActorModel> actorMap;
	private List<Integer> active;
	
	ActorCompositeModel(Map<Integer,ActorModel> actorMap, List<Integer> activeList){	
		set(actorMap, activeList);	
	}

	/**
	 * @param actorMap
	 * @param activeList
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
	
	/**
	 * @param distance
	 */
	public void moveForward(double distance){
		actors().forEach((actor)-> actor.moveForward(distance));
	}
	
	/**
	 * @param distance
	 */
	public void moveBackward(double distance){
		actors().forEach((actor)->actor.moveForward(-distance));
	}
	
	/**
	 * @param v
	 */
	public void setVisible(boolean v) {
		actors().forEach((actor)->actor.setVisible(v));
	}

	/**
	 * @return
	 */
	public boolean getPenUp() {	
		return lastActor().getPenUp();
	}
	
	/**
	 * @return
	 */
	public Point2D.Double getPos() {	
		return lastActor().getPos();
	}
	/**
	 * @param pos
	 */
	public void setPos(Point2D.Double pos) {
		actors().forEach((actor)->actor.setPos(pos));
	}
	/**
	 * @return
	 */
	public double getHeading() {
		return lastActor().getHeading();
	}
	/**
	 * @param heading
	 */
	public void setHeading(Double heading) {
		actors().forEach((actor)->actor.setHeading(heading));
	}
	/**
	 * @param penUp
	 */
	public void setPenUp(boolean penUp) {
		actors().forEach((actor)->actor.setPenUp(penUp));
	}

	/**
	 * @return
	 */
	public boolean getVisible() {
		return lastActor().getVisible();
	}

	/**
	 * @param distance
	 */
	public void rotate(double distance) {
		actors().forEach((actor)->actor.setHeading(actor.getHeading()+distance));
	}
	
	/**
	 * @param a
	 * @param b
	 */
	public void setTowards(double a, double b) {
		actors().forEach((actor)->setToward(actor,a,b));
	}
	
	private void setToward(ActorModel actor, double a, double b){
		double newAngle = Math.toDegrees(Math.atan(actor.getPos().getY() / actor.getPos().getX()));
		actor.setHeading(newAngle);
	}

	/**
	 * @return
	 */
	public double getId() {
		return active.get(active.size()-1);
	}
	
}