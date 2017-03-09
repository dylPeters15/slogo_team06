package backend.states;

import javafx.scene.paint.Color;

/**
 * @author Tavo
 *
 */


public class State {
	
	private ActorModel actor;
	private boolean clear;
	private ColorList bgColor;
	private ColorList penColor;
	private Double penSize;
	private int turtleShape;
	
	public State(State state){
		actor = new TurtleModel(state.getActor());
		clear = false;
		bgColor = state.getBGColorList();
		penColor = state.getPenColorList();
		penSize = state.getPenSize();
	}
	
	public State() {
		actor = new TurtleModel();
		clear = false;
		bgColor = ColorList.WHITE;
		penColor = ColorList.BLACK;
		penSize = 1.0;
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
	
	public ColorList getBGColorList() {
		return bgColor;
	}
	
	public Color getBGColor() {
		return bgColor.getColor();
	}
	
	public void setBGColor(ColorList color) {
		bgColor = color;
	}
	
	public ColorList getPenColorList() {
		return penColor;
	}
	
	public Color getPenColor() {
		return penColor.getColor();
	}
	
	public void setPenColor(ColorList color) {
		penColor = color;
	}
	
	public Double getPenSize() {
		return penSize;
	}
	
	public void setPenSize(double size) {
		penSize = size;
	}
	
	public Double getShape() {
		return (double)turtleShape;
	}
	
	public void setShape(int shape) {
		turtleShape = shape;
	}
}

