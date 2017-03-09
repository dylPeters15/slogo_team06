package frontend.simulation;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.states.ActorModel;
import backend.states.State;
import backend.states.StatesList;
import frontend.SlogoBaseUIManager;

/**
 * Manager for simulation window
 * This class has public visibility, and thus adds to the External Frontend API
 * 
 * The class is designed to setup and manage the simulation window of the Frontend
 * implementation. It has a MenuBar and an Environment Display. It also implements
 * a SimulationMenuBarDelegate in order to communicate with it's menu bar.
 * @author Andreas Santos
 *
 */
public class SimulationPaneManager extends SlogoBaseUIManager<Region> 
								implements SimulationMenuBarDelegate, ListChangeListener<State>  {
	
	private BorderPane borderPane;
	
	private SimulationMenuBarManager simulationMenuBarManager;
	private EnvironmentDisplayManager environmentDisplayManager;
	private StatesList<State> statesList;
	private Map<Integer, ActorModel> myActorsMap;
	private HashMap<Integer, TurtleView> myTurtleViewMap;
	private List<Integer> myActiveList;
	
	public SimulationPaneManager(StatesList<State> s){
		initialize();
		statesList = s;
		statesList.addListener(this);
	}

	/**
	 * Sets the width of the Parent object that holds all of the UI components.
	 * @param width
	 */
	public void setWidth(double width){
		
	}
	
	/**
	 * Gets the width of the Parent object that holds all of the UI components.
	 * @return
	 * 		double width
	 */
    public double getWidth(){
    	return 600;
    }
    
    /**
     * Sets the height of the Parent object that holds all of the UI components.
     * @param height
     */
    public void setHeight(double height){
    	
    }
    
    /**
     * Gets the height of the Parent object that holds all of the UI components.
     * @return
     * 		double height
     */
    public double getHeight(){
    	return 600;
    }
    
    /**
     * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor. It can be used as the root of a Scene or
	 * added as a component in a larger display.
	 * 
     * @return 
     * 		Parent
     */
    @Override
    public Region getObject(){
    	return borderPane;
    }
    
    /**
	 * This method is called to reset the turtle at
	 * the home location (this may potentially return
	 * a double value later)
	 */
    public void home(){
    	environmentDisplayManager.home();
    }
	
    /**
	 * This method is called to change the 
	 * Background color of the simulation's
	 * environment display
	 * @param color
	 */
	public void setBackgroundColor(Color color){
		environmentDisplayManager.setBackgroundColor(color);
	}
	
	/**
	 * This method is called to change the image 
	 * used for the actor/turtle
	 * @param image
	 */
	public void setTurtleImage(Image image){
		environmentDisplayManager.setTurtleImage(image);
	}
	
	private void initialize() {
		
		borderPane = new BorderPane();
		
		myActorsMap = new HashMap<Integer, ActorModel>();
		myActiveList = new ArrayList<Integer>();
		myTurtleViewMap = new HashMap<Integer, TurtleView>();
		simulationMenuBarManager = new SimulationMenuBarManager(this);
		environmentDisplayManager = new EnvironmentDisplayManager(600, 600);
		
		borderPane.setTop(simulationMenuBarManager.getObject());
		borderPane.setBottom(environmentDisplayManager.getObject());
		
		setStyleSheet(getStyleSheet());
		
		simulationMenuBarManager.getObject().prefWidthProperty().bind(borderPane.widthProperty());
		environmentDisplayManager.getObject().prefHeightProperty().bind(borderPane.heightProperty().subtract(simulationMenuBarManager.getObject().heightProperty()));
		environmentDisplayManager.getObject().prefWidthProperty().bind(borderPane.widthProperty());
		
		borderPane.setPrefWidth(getWidth());
		borderPane.setPrefHeight(getHeight());
		
	}

	/**
	 * This method is called to change the 
	 * Pen color of the simulation's
	 * environment display
	 * @param color
	 */
	public void setPenColor(Color color) {
		environmentDisplayManager.setPenColor(color);	
	}
	
	@Override
	public void onChanged(ListChangeListener.Change<? extends State> c) {
		while (!statesList.isEmpty()){
			State state = statesList.poll();
			Color previousColor = Color.WHITE;
			if (state.clearscreen()){
				environmentDisplayManager.clearScreen();
			} else {
				environmentDisplayManager.setPenColor(state.getPenColor());
				environmentDisplayManager.setPenWidth(state.getPenSize());
				
				if (!state.getBGColor().equals(previousColor)){
					environmentDisplayManager.setBackgroundColor(state.getBGColor());
					previousColor = state.getBGColor();
				}
				
				myActorsMap = state.getActorMap();
				myActiveList = state.getActiveList();
				updateTurtleViewMap();
				//environmentDisplayManager.getTurtle().update(state.getActor());
				environmentDisplayManager.updateTurtles(myActiveList, myActorsMap, myTurtleViewMap);
			}
		}
	}

	void updateTurtleViewMap() {
		for (Integer i : myActorsMap.keySet()){
			if (! myTurtleViewMap.containsKey(i)){
				TurtleView t = new TurtleView();
				t.setPosition(environmentDisplayManager.convertXCoordinate(t.getX()),
						environmentDisplayManager.convertYCoordinate(t.getY()));
				myTurtleViewMap.put(i, t);
			}
		}
	}

//	@Override
//	protected void styleSheetDidChange(){
//		borderPane.getStylesheets().clear();
//		borderPane.getStylesheets().add(getStyleSheet());
//	}
	
	@Override
	protected void languageResourceBundleDidChange() {
		simulationMenuBarManager.setLanguageResourceBundle(getLanguageResourceBundle());
	}

}
