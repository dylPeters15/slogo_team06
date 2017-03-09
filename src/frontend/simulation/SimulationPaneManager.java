package frontend.simulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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

	private SimulationMenuBarManager simulationMenuBarManager;
	private EnvironmentDisplayManager environmentDisplayManager;
	private StatesList<State> statesList;

	public SimulationPaneManager(StatesList<State> s) {
		initialize();
		statesList = s;
		statesList.addListener(this);
	}

	/**
	 * Sets the width of the Parent object that holds all of the UI components.
	 * 
	 * @param width
	 */
	public void setWidth(double width) {

	}

	/**
	 * Gets the width of the Parent object that holds all of the UI components.
	 * 
	 * @return double width
	 */
	public double getWidth() {
		return 600;
	}

	/**
	 * Sets the height of the Parent object that holds all of the UI components.
	 * 
	 * @param height
	 */
	public void setHeight(double height) {

	}

	/**
	 * Gets the height of the Parent object that holds all of the UI components.
	 * 
	 * @return double height
	 */
	public double getHeight() {
		return 600;
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

		borderPane = new BorderPane();

		simulationMenuBarManager = new SimulationMenuBarManager();
		environmentDisplayManager = new EnvironmentDisplayManager(600, 600);

		borderPane.setTop(simulationMenuBarManager.getObject());
		borderPane.setBottom(environmentDisplayManager.getObject());

		simulationMenuBarManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());
		environmentDisplayManager
				.getObject()
				.prefHeightProperty()
				.bind(borderPane.heightProperty().subtract(
						simulationMenuBarManager.getObject().heightProperty()));
		environmentDisplayManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());

		borderPane.setPrefWidth(getWidth());
		borderPane.setPrefHeight(getHeight());
		
		simulationMenuBarManager.getLanguage().bind(getLanguage());
		simulationMenuBarManager.getStyleSheet().bind(getStyleSheet());
		environmentDisplayManager.getLanguage().bind(getLanguage());
		environmentDisplayManager.getStyleSheet().bind(getStyleSheet());
		
		simulationMenuBarManager.getHome().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldVal, Boolean newVal) {
				if (!oldVal && newVal){
					home();
				}
			}
		});
		simulationMenuBarManager.getPenColor().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observable,
					Color oldVal, Color newVal) {
				setPenColor(newVal);
			}
		});
		simulationMenuBarManager.getBackgroundColor().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observable,
					Color oldVal, Color newVal) {
				setBackgroundColor(newVal);
			}
		});
		simulationMenuBarManager.getTurtleImage().addListener(new ChangeListener<Image>() {
			@Override
			public void changed(ObservableValue<? extends Image> observable,
					Image oldVal, Image newVal) {
				setTurtleImage(newVal);
			}
		});

	}

	/**
	 * This method is called to change the Pen color of the simulation's
	 * environment display
	 * 
	 * @param color
	 */
	public void setPenColor(Color color) {
		environmentDisplayManager.setPenColor(color);
	}

	@Override
	public void onChanged(ListChangeListener.Change<? extends State> c) {
		while (!statesList.isEmpty()) {
			State state = statesList.poll();
			if (state.clearscreen()) {
				environmentDisplayManager.clearScreen();
			} else {
				// environmentDisplayManager.setPenColor(state.getPenColor());
				// environmentDisplayManager.setPenWidth(state.getPenSize());
				// environmentDisplayManager.setBackgroundColor(state.getBGColor());
				environmentDisplayManager.getTurtle().update(state.getActor());
				environmentDisplayManager.updateTurtle();
			}
		}
	}

}
