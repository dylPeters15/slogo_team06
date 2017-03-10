package backend.states;

import java.awt.geom.Point2D;

/**
 * The class to represent actor model.
 * 
 */
public abstract class ActorModel {
	private Point2D.Double pos;
	private Double heading;
	private boolean penUp;
	private boolean visible;
	
	public static final Point2D.Double DEF_POS = new Point2D.Double(0,0);
	public static final boolean DEF_PENUP = false;
	public static final double DEF_HEADING = 0;
	public static final boolean DEF_VISIBILITY = true;
	
	/**
	 * Default constructor of the ActorModel class.
	 */
	public ActorModel(){
		this(DEF_POS.getX(),DEF_POS.getX(),DEF_HEADING);
		penUp = DEF_PENUP;
		visible = DEF_VISIBILITY;
	}
	
	/**
	 * Constructor of the ActorModel class.
	 * @param x: actor's x position
	 * @param y: actor's y position
	 * @param heading: actor's heading
	 */
	public ActorModel(double x, double y, double heading){
		setVector(x, y, heading);
	}
	
	/**
	 * Constructor of the ActorModel class.
	 * @param actor: an existing ActorModel instance.
	 */
	public ActorModel(ActorModel actor){
		pos = actor.getPos();
		heading = actor.getHeading();
		penUp = actor.getPenUp();
		visible = actor.getVisible();
	}
	
	/**
	 * The method to set the x position, y position, and heading of the ActorModel.
	 * @param x
	 * @param y
	 * @param heading
	 */
	protected void setVector(double x, double y, double heading) {
		pos = new Point2D.Double((int)x,(int)y);
		this.heading = heading;
	}	
	
    private double format(double value) {
        return (double)Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }
	
    /**
     * The method to move the ActorModel forward by distance.
     * @param distance
     */
	public void moveForward(double distance){
		double x = pos.getX();
		double y = pos.getY();
		pos.setLocation(x + format(Math.cos(Math.toRadians(heading)))*distance,
						y + format(Math.sin(Math.toRadians(heading)))*distance);
	}
	
	/**
     * The method to move the ActorModel backward by distance.
     * @param distance
     */
	public void moveBackward(double distance){
		moveForward(-distance);
	}
	
	public Point2D.Double getPos() {
		return pos;
	}
	
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}
	
	/**
	 * The method to get the heading of the ActorModel.
	 * @return heading in angle
	 */
	public double getHeading() {
		return heading;
	}
	
	/**
	 * The method to set the heading of the ActorModel.
	 * @param heading: heading in angle
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

	public boolean getPenUp() {
		return penUp;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean v) {
		visible = v;
	}
	
}