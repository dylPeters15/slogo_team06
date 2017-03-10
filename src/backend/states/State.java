package backend.states;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The class to represent a state of the turtle.
 * A new state instant will be generated after a new command is parsed.
 * States will be read by the front end to display on the GUI.
 * @author Tavo
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
	 * Constructor of the State class.
	 * Take an existing state to initialize the new one.
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
	 * Default constructor of the State class.
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
	 * The method to add a new actor to the internal map.
	 */
	public void addActor(){
		maxId++;
		setActor(maxId,new TurtleModel());
	}
	
	/**
	 * The method to add an input actor to the internal map.
	 * @param actor
	 */
	public void addActor(ActorModel actor){
		maxId++;
		setActor(maxId,actor);
	}
	
	/**
	 * The method to add a pair of id and actor to the internal map.
	 * @param id
	 * @param actor
	 */
	public void addActor(int id, ActorModel actor){
		ActorModel newActor = new TurtleModel(actor);
		actors.put(id, newActor);
	}
	
	/**
	 * The method to put a pair of id and actor to the internal map.
	 * @param id
	 * @param actor
	 */
	public void setActor(int id, ActorModel actor){
		actors.put(id, actor);
	}
	
	
	/**
	 * @return the activeList of current active turtles
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

	/**
	 * The method to get the maximum turtle id.
	 * @return
	 */
	public int getMaxId() {
		return maxId;
	}

	/**
	 * The method to get the actor map.
	 * @return
	 */
	public Map<Integer, ActorModel> getActorMap() {
		return actors;
	}

	/**
	 * The method to set the current actorList map.
	 * @param actors
	 */
	public void setActors(Map<Integer, ActorModel> actors) {
		this.actors = actors;
	}

	/**
	 * The method to get the default actor.
	 * @return
	 */
	public ActorModel getActor() {
		return actors.get(DEF_ID);
	}
	
	/**
	 * The method to get the actor composite model.
	 * @return
	 */
	public ActorCompositeModel getActors() {
		return multiActor;
	}
	
	/**
	 * The method to set the default actorModel.
	 * @param actorModel
	 */
	public void setActor(ActorModel actorModel) {
		actors.put(DEF_ID, actorModel);
	}

	/**
	 * The method to get the number of turtles.
	 * @return
	 */
	public double getNumTurtles() {
		return actors.keySet().size();
	}
	
	/**
	 * The method to get if the command is to clear the screen.
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
	
	/**
	 * The method to get the current background color.
	 * @return javafx.scene.paint.Color
	 */
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
	
	/**
	 * The method to get the current pen color.
	 * @return javafx.scene.paint.Color
	 */
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
	
	/**
	 * The method to get the current pen size.
	 * @return Double
	 */
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
	
	public ShapeList getTurtleShape() {
		return turtleShape;
	}
	
	/**
	 * The method to get the current Shape.
	 * @return javafx.scene.image.Image
	 */
	public Image getTurtleShapeImage() {
		return turtleShape.getImage();
	}
	
	public void setTurtleShape(ShapeList shape) {
		turtleShape = shape;
	}

}

