package backend.states;

import javafx.scene.image.Image;
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
	private ShapeList turtleShape;
	
	/**
	 * @param state
	 */
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
	
	/**
	 * 
	 */
	public void addActor(){
		maxId++;
		setActor(maxId,new TurtleModel());
	}
	
	/**
	 * @param actor
	 */
	public void addActor(ActorModel actor){
		maxId++;
		setActor(maxId,actor);
	}
	
	/**
	 * @param id
	 * @param actor
	 */
	public void addActor(int id, ActorModel actor){
		ActorModel newActor = new TurtleModel(actor);
		actors.put(id, newActor);
	}
	
	/**
	 * @param id
	 * @param actor
	 */
	public void setActor(int id, ActorModel actor){
		actors.put(id, actor);
	}
	
	/**
	 * 
	 */
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
		turtleShape = ShapeList.SHAPE1;
		actors = new HashMap<Integer,ActorModel>();
		activeList = new ArrayList<Integer>();
		activeList.add(1);
		multiActor = new ActorCompositeModel(actors, activeList);
	}
	
	/**
	 * @return the activeList
	 */
	/**
	 * @return
	 */
	public List<Integer> getActiveList() {
		return activeList;
	}

	/**
	 * @param activeList the activeList to set
	 */
	/**
	 * @param activeList
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

	/**
	 * @return
	 */
	public int getMaxId() {
		return maxId;
	}

	/**
	 * @return
	 */
	public Map<Integer, ActorModel> getActorMap() {
		return actors;
	}

	/**
	 * @param actors
	 */
	public void setActors(Map<Integer, ActorModel> actors) {
		this.actors = actors;
	}

	/**
	 * @return
	 */
	public ActorModel getActor() {
		return actors.get(DEF_ID);
	}
	
	/**
	 * @return
	 */
	public ActorCompositeModel getActors() {
		return multiActor;
	}
	
	/**
	 * @param actorModel
	 */
	public void setActor(ActorModel actorModel) {
		actors.put(DEF_ID, actorModel);
	}

	/**
	 * @return the clear
	 */
	/**
	 * @return
	 */
	public boolean clearscreen() {
		return clear;
	}

	/**
	 * @param clear the clear to set
	 */
	/**
	 * @param clear
	 */
	public void setClear(boolean clear) {
		this.clear = clear;
	}

	/**
	 * @return
	 */
	public boolean getBGColorChanged() {
		return bgColorChanged;
	}
	
	/**
	 * @return
	 */
	public ColorList getBGColorList() {
		return bgColor;
	}
	
	/**
	 * @return
	 */
	public Color getBGColor() {
		return bgColor.getColor();
	}
	
	/**
	 * @param changed
	 */
	public void setBGColorChanged(boolean changed) {
		bgColorChanged = changed;
	}
	
	/**
	 * @param color
	 */
	public void setBGColor(ColorList color) {
		bgColor = color;
	}
	
	/**
	 * @return
	 */
	public boolean getPenColorChanged() {
		return penColorChanged;
	}
	
	/**
	 * @return
	 */
	public ColorList getPenColorList() {
		return penColor;
	}
	
	/**
	 * @return
	 */
	public Color getPenColor() {
		return penColor.getColor();
	}
	
	/**
	 * @param changed
	 */
	public void setPenColorChanged(boolean changed) {
		penColorChanged = changed;
	}
	
	/**
	 * @param color
	 */
	public void setPenColor(ColorList color) {
		penColor = color;
	}
	
	/**
	 * @return
	 */
	public boolean getPenSizeChanged() {
		return penSizeChanged;
	}
	
	/**
	 * @return
	 */
	public Double getPenSize() {
		return penSize;
	}
	
	/**
	 * @param changed
	 */
	public void setPenSizeChanged(boolean changed) {
		penSizeChanged = changed;
	}
	
	/**
	 * @param size
	 */
	public void setPenSize(double size) {
		penSize = size;
	}
	
	/**
	 * @return
	 */
	public boolean getTurtleShapeChanged() {
		return turtleShapeChanged;
	}
	
	/**
	 * @param changed
	 */
	public void setTurtleShapeChanged(boolean changed) {
		turtleShapeChanged = changed;
	}
	
	/**
	 * @return
	 */
	public ShapeList getTurtleShape() {
		return turtleShape;
	}
	
	/**
	 * @return
	 */
	public Image getTurtleShapeImage() {
		return turtleShape.getImage();
	}
	
	/**
	 * @param shape
	 */
	public void setTurtleShape(ShapeList shape) {
		turtleShape = shape;
	}

	/**
	 * @return
	 */
	public double getNumTurtles() {
		return actors.keySet().size();
	}
}

