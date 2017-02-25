package backend.states;

import java.awt.Point;

public abstract class ActorModel {
	private Point pos;
	private Double heading;
	private boolean penUp;
	private boolean visible;
	
	public static final Point DEF_POS = new Point(0,0);
	public static final boolean DEF_PENUP = false;
	public static final double DEF_HEADING = 0;
	public static final boolean DEF_VISIBILITY = true;
	
	ActorModel(){
		this(DEF_POS.getX(),DEF_POS.getX(),DEF_HEADING);
		penUp = DEF_PENUP;
		visible = DEF_VISIBILITY;
	}
	
	ActorModel(double x, double y, double heading){
		pos = new Point((int)x,(int)y);
		this.heading = heading;
	}		
	
	ActorModel(ActorModel actor){
		initFromActor(actor);
	}

	public void initFromActor(ActorModel actor){
		pos = actor.getPos();
		heading = actor.getHeading();
		penUp = actor.isPenUp();
		visible = actor.isVisible();
	}
	
	public void moveForward(double distance){
		double x = pos.getX();
		double y = pos.getY();
		pos.setLocation(x + Math.cos(heading)*distance,
						y + Math.sin(heading)*distance);	
	}
	
	public void moveBackward(double distance){
		moveForward(-distance);
	}
	
	private boolean isVisible() {
		return visible;
	}

	private boolean isPenUp() {
		return penUp;
	}
	
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public Double getHeading() {
		return heading;
	}
	public void setHeading(Double heading) {
		this.heading = heading%360;
	}
}
