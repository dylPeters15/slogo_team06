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

	/**
	 * @param actor
	 */
	public void initFromActor(ActorModel actor){
		pos = actor.getPos();
		heading = actor.getHeading();
		penUp = actor.getPenUp();
		visible = actor.getVisible();
	}
	
	/**
	 * @param distance
	 */
	public void moveForward(double distance){
		double x = pos.getX();
		double y = pos.getY();
		pos.setLocation(x + format(Math.cos(Math.toRadians(heading)))*distance,
						y + format(Math.sin(Math.toRadians(heading)))*distance);
	}
	
    private double format(double value) {
        return (double)Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }
	
	/**
	 * @param distance
	 */
	public void moveBackward(double distance){
		moveForward(-distance);
	}
	
	/**
	 * @return
	 */
	public boolean getVisible() {
		return visible;
	}
	/**
	 * @param v
	 */
	public void setVisible(boolean v) {
		visible = v;
	}

	/**
	 * @return
	 */
	public boolean getPenUp() {
		return penUp;
	}
	
	/**
	 * @return
	 */
	public Point2D.Double getPos() {
		return pos;
	}
	/**
	 * @param pos
	 */
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}
	/**
	 * @return
	 */
	public double getHeading() {
		return heading;
	}
	/**
	 * @param heading
	 */
	public void setHeading(Double heading) {
		this.heading = heading%360;
	}
	/**
	 * @param penUp
	 */
	public void setPenUp(boolean penUp) {
		this.penUp = penUp;
	}
	
}