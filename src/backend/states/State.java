package backend.states;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tavo
 *
 */


public class State {
	
	private int maxId;
	private static final int DEF_ID = 1;
	
	private Map<Integer, ActorModel> actors;
	private List<Integer> activeList;
	private ActorCompositeModel multiActor;
	private boolean clear;
	private boolean bgColorChanged;
	private ColorList bgColor;
	private boolean penColorChanged;
	private ColorList penColor;
	private boolean penSizeChanged;
	private Double penSize;
	private boolean turtleShapeChanged;
	private int turtleShape;
	
	public State(State state){
	    initFields();
	    // default changed indicators are all false;
		bgColor = state.getBGColorList();
		penColor = state.getPenColorList();
		penSize = state.getPenSize();
		turtleShape = state.getTurtleShape();
		setActiveList(state.getActiveList());
		multiActor.set(actors, activeList);
		maxId = state.getMaxId();
		state.getActorMap().keySet().forEach((id)-> addActor(id,state.getActorMap().get(id)));	
	}
	
	public void addActor(){
		maxId++;
		setActor(maxId,new TurtleModel());
	}
	
	public void addActor(ActorModel actor){
		maxId++;
		setActor(maxId,actor);
	}
	
	public void addActor(int id, ActorModel actor){
		ActorModel newActor = new TurtleModel(actor);
		actors.put(id, newActor);
	}
	
	public void setActor(int id, ActorModel actor){
		actors.put(id, actor);
	}
	
	public State() {
		initFields();
		addActor();
	}

	private void initFields() {
		maxId = 0;
		clear = false;
		bgColorChanged = false;
		bgColor = ColorList.WHITE;
		penColorChanged = false;
		penColor = ColorList.BLACK;
		penSizeChanged = false;
		penSize = 1.0;
		turtleShapeChanged = false;
		turtleShape = 0;
		actors = new HashMap<Integer,ActorModel>();
		activeList = new ArrayList<Integer>();
		activeList.add(1);
		multiActor = new ActorCompositeModel(actors, activeList);
	}
	
	/**
	 * @return the activeList
	 */
	public List<Integer> getActiveList() {
		return activeList;
	}

	/**
	 * @param activeList the activeList to set
	 */
	public void setActiveList(List<Integer> activeList) {
		this.activeList = activeList;
		activeList.forEach((id)->addActorIfNewId(id));
		multiActor.set(actors, activeList);
	}
	
	private void addActorIfNewId(int id){
		if(!actors.containsKey(id)){
			setActor(id,new TurtleModel());
			if(id>maxId){
				maxId = id;
			}
		}
	}

	public int getMaxId() {
		return maxId;
	}

	public Map<Integer, ActorModel> getActorMap() {
		return actors;
	}

	public void setActors(Map<Integer, ActorModel> actors) {
		this.actors = actors;
	}

	public ActorModel getActor() {
		return actors.get(DEF_ID);
	}
	
	public ActorCompositeModel getActors() {
		return multiActor;
	}
	
	public void setActor(ActorModel actorModel) {
		actors.put(DEF_ID, actorModel);
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

	public boolean getBGColorChanged() {
		return bgColorChanged;
	}
	
	public ColorList getBGColorList() {
		return bgColor;
	}
	
	public Color getBGColor() {
		return bgColor.getColor();
	}
	
	public void setBGColorChanged(boolean changed) {
		bgColorChanged = changed;
	}
	
	public void setBGColor(ColorList color) {
		bgColor = color;
	}
	
	public boolean getPenColorChanged() {
		return penColorChanged;
	}
	
	public ColorList getPenColorList() {
		return penColor;
	}
	
	public Color getPenColor() {
		return penColor.getColor();
	}
	
	public void setPenColorChanged(boolean changed) {
		penColorChanged = changed;
	}
	
	public void setPenColor(ColorList color) {
		penColor = color;
	}
	
	public boolean getPenSizeChanged() {
		return penSizeChanged;
	}
	
	public Double getPenSize() {
		return penSize;
	}
	
	public void setPenSizeChanged(boolean changed) {
		penSizeChanged = changed;
	}
	
	public void setPenSize(double size) {
		penSize = size;
	}
	
	public boolean getTurtleShapeChanged() {
		return turtleShapeChanged;
	}
	
	public void setTurtleShapeChanged(boolean changed) {
		turtleShapeChanged = changed;
	}
	
	public int getTurtleShape() {
		return turtleShape;
	}
	
	public void setTurtleShape(int shape) {
		turtleShape = shape;
	}

	public double getNumTurtles() {
		return actors.keySet().size();
	}
}

