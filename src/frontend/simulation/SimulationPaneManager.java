package frontend.simulation;

import backend.states.State;
import backend.states.StatesList;
import javafx.collections.ListChangeListener;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

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
public class SimulationPaneManager implements SimulationMenuBarDelegate, ListChangeListener<State> {
	private static final double DEFAULT_WIDTH = 1000;
	private static final double DEFAULT_HEIGHT = 1000;
	
	private static final String DEFAULT_STYLE_SHEET = "resources/default.css";
	
	private BorderPane borderPane;
	
	private SimulationMenuBarManager simulationMenuBarManager;
	private EnvironmentDisplayManager environmentDisplayManager;
	private StatesList<State> statesList;
	
	// Constructors that will be present when this interface is turned into a
	// class:
	public SimulationPaneManager(StatesList<State> s){
		initialize();
		statesList = s;
		statesList.addListener(this);
	}
	// public SimulationPaneManager(double width, double height);
	
	
	
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
    	return 0;
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
    	return 0;
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
    public Parent getParent(){
    	return borderPane;
    }
    
	public void setStyleSheet(String styleSheet) {
		borderPane.getStylesheets().clear();
		borderPane.getStylesheets().add(styleSheet);
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
		
		simulationMenuBarManager = new SimulationMenuBarManager(this);
		environmentDisplayManager = new EnvironmentDisplayManager(600, 600);
		
		borderPane.setTop(simulationMenuBarManager.getRegion());
		borderPane.setBottom(environmentDisplayManager.getRegion());
		
		setStyleSheet(DEFAULT_STYLE_SHEET);
		
		simulationMenuBarManager.getRegion().prefWidthProperty().bind(borderPane.widthProperty());
		environmentDisplayManager.getRegion().prefHeightProperty().bind(borderPane.heightProperty().subtract(simulationMenuBarManager.getRegion().heightProperty()));
		environmentDisplayManager.getRegion().prefWidthProperty().bind(borderPane.widthProperty());
		
		borderPane.setPrefWidth(DEFAULT_WIDTH);
		borderPane.setPrefHeight(DEFAULT_HEIGHT);
		
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
			if (state.clearscreen()){
				environmentDisplayManager.clearScreen();
			} else {
				environmentDisplayManager.getTurtle().update(state.getActor());
				environmentDisplayManager.updateTurtle();
			}
		}
	}
}
