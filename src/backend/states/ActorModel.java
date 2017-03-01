package backend.states;

import java.awt.geom.Point2D;

public abstract class ActorModel {
	private Point2D.Double pos;
	private Double heading;
	private boolean penUp;
	private boolean visible;
	
	public static final Point2D.Double DEF_POS = new Point2D.Double(0,0);
	public static final boolean DEF_PENUP = false;
	public static final double DEF_HEADING = 0;
	public static final boolean DEF_VISIBILITY = true;
	
	ActorModel(){
		this(DEF_POS.getX(),DEF_POS.getX(),DEF_HEADING);
		penUp = DEF_PENUP;
		visible = DEF_VISIBILITY;
	}
	
	ActorModel(double x, double y, double heading){
		setVector(x, y, heading);
	}

	protected void setVector(double x, double y, double heading) {
		pos = new Point2D.Double((int)x,(int)y);
		this.heading = heading;
	}		
	
	ActorModel(ActorModel actor){
		initFromActor(actor);
	}

	public void initFromActor(ActorModel actor){
		pos = actor.getPos();
		heading = actor.getHeading();
		penUp = actor.getPenUp();
		visible = actor.getVisible();
	}
	
	public void moveForward(double distance){
		double x = pos.getX();
		double y = pos.getY();
		System.out.println("    Distance: "+distance);
		System.out.println("\t Before: ("+x+","+y+")");
		pos.setLocation(x + Math.cos(heading)*distance,
						y + Math.sin(heading)*distance);
		System.out.println("\t After: ("+pos.getX()+","+pos.getY()+")");
	}
	
	public void moveBackward(double distance){
		moveForward(-distance);
	}
	
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean v) {
		visible = v;
	}

	public boolean getPenUp() {
		return penUp;
	}
	
	public Point2D.Double getPos() {
		return pos;
	}
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(Double heading) {
		this.heading = heading%360;
	}
	public void setPenUp(boolean penUp) {
		this.penUp = penUp;
	}
	
}