package frontend.simulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
 * Manager for simulation window This class has public visibility, and thus adds
 * to the External Frontend API
 * 
 * The class is designed to setup and manage the simulation window of the
 * Frontend implementation. It has a MenuBar and an Environment Display. It also
 * implements a SimulationMenuBarDelegate in order to communicate with it's menu
 * bar.
 * 
 * @author Andreas Santos
 *
 */
public class SimulationPaneManager extends SlogoBaseUIManager<Region> implements
		ListChangeListener<State> {

	private BorderPane borderPane;
	private static final double WIDTH = 600;
	private static final double HEIGHT = 600;

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
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor. It can be used as the root of a Scene or
	 * added as a component in a larger display.
	 * 
	 * @return Parent
	 */
	@Override
	public Region getObject() {
		return borderPane;
	}

	/**
	 * This method is called to reset the turtle at the home location (this may
	 * potentially return a double value later)
	 */
	public void home() {
		environmentDisplayManager.home();
	}

	/**
	 * This method is called to change the Background color of the simulation's
	 * environment display
	 * 
	 * @param color
	 */
	public void setBackgroundColor(Color color) {
		environmentDisplayManager.setBackgroundColor(color);
	}

	/**
	 * This method is called to change the image used for the actor/turtle
	 * 
	 * @param image
	 */
	public void setTurtleImage(Image image) {
		environmentDisplayManager.setTurtleImage(image);
	}

	private void initialize() {
		initDataStructures();
		makeManagers();
		initPane();
		bindManagers();
		
		addHomeListener();
		addPenColorListener();
		addBackgroundColorListener();
		addTurtleImageListener();
	}

	private void addTurtleImageListener() {
		simulationMenuBarManager.getTurtleImage().addListener(new ChangeListener<Image>() {
			@Override
			public void changed(ObservableValue<? extends Image> observable,
					Image oldVal, Image newVal) {
				setTurtleImage(newVal);
			}
		});
	}

	private void addBackgroundColorListener() {
		simulationMenuBarManager.getBackgroundColor().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observable,
					Color oldVal, Color newVal) {
				setBackgroundColor(newVal);
			}
		});
	}

	private void addPenColorListener() {
		simulationMenuBarManager.getPenColor().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observable,
					Color oldVal, Color newVal) {
				setPenColor(newVal);
			}
		});
	}

	private void addHomeListener() {
		simulationMenuBarManager.getHome().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldVal, Boolean newVal) {
				if (!oldVal && newVal){
					home();
				}
			}
		});
	}

	private void bindManagers() {
		simulationMenuBarManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());
		environmentDisplayManager
				.getObject()
				.prefHeightProperty()
				.bind(borderPane.heightProperty().subtract(
						simulationMenuBarManager.getObject().heightProperty()));
		environmentDisplayManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());
		simulationMenuBarManager.getLanguage().bind(getLanguage());
		simulationMenuBarManager.getStyleSheet().bind(getStyleSheet());
		environmentDisplayManager.getLanguage().bind(getLanguage());
		environmentDisplayManager.getStyleSheet().bind(getStyleSheet());
	}

	private void initPane() {
		borderPane = new BorderPane();
		borderPane.setTop(simulationMenuBarManager.getObject());
		borderPane.setBottom(environmentDisplayManager.getObject());
		borderPane.setPrefWidth(WIDTH);
		borderPane.setPrefHeight(HEIGHT);
	}

	private void makeManagers() {
		simulationMenuBarManager = new SimulationMenuBarManager();
		environmentDisplayManager = new EnvironmentDisplayManager();
	}

	private void initDataStructures() {
		myActorsMap = new HashMap<Integer, ActorModel>();
		myActiveList = new ArrayList<Integer>();
		myTurtleViewMap = new HashMap<Integer, TurtleView>();
	}

	/**
	 * This method is called to change the Pen color of the simulation's
	 * environment display
	 * 
	 * @param color
	 */
	private void setPenColor(Color color) {
		environmentDisplayManager.setPenColor(color);
	}

	@Override
	public void onChanged(ListChangeListener.Change<? extends State> c) {
		while (!statesList.isEmpty()) {
			State state = statesList.poll();
			if (state.clearscreen()){
				environmentDisplayManager.clearScreen();
			} else {
				changePenColor(state);
				changePenSize(state);
				changeBackgroundColor(state);
				changeTurtleShape(state);
				updateDataStructures(state);
				environmentDisplayManager.updateTurtles(myActiveList, myActorsMap, myTurtleViewMap);
			}
		}
	}

	private void updateDataStructures(State state) {
		myActorsMap = state.getActorMap();
		myActiveList = state.getActiveList();
		updateTurtleViewMap();
	}

	private void changeTurtleShape(State state) {
		if (state.getTurtleShapeChanged()){
			environmentDisplayManager.setTurtleImage(state.getTurtleShapeImage());
		}
	}

	private void changeBackgroundColor(State state) {
		if (state.getBGColorChanged()){
			environmentDisplayManager.setBackgroundColor(state.getBGColor());
		}
	}

	private void changePenSize(State state) {
		if (state.getPenSizeChanged()){
			environmentDisplayManager.setPenWidth(state.getPenSize());
		}
	}

	private void changePenColor(State state) {
		if (state.getPenColorChanged()){
			environmentDisplayManager.setPenColor(state.getPenColor());
		}
	}

	private void updateTurtleViewMap() {
		for (Integer i : myActorsMap.keySet()){
			if (! myTurtleViewMap.containsKey(i)){
				TurtleView t = new TurtleView();
				t.setPosition(environmentDisplayManager.convertXCoordinate(t.getX()),
						environmentDisplayManager.convertYCoordinate(t.getY()));
				myTurtleViewMap.put(i, t);
			}
		}
	}

}