package frontend.simulation;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a menu bar to be used in the
 * SimulationPaneManager. The goal of this class is to hide the details of
 * implementation required to display buttons and other Control objects required
 * for the user to interact with in the simulation
 * 
 * @author Andreas
 *
 */
class SimulationMenuBarManager extends SimulationPaneManagerChild<SimulationMenuBarDelegate> {
	private ObservableList<String> colors = FXCollections
			.observableArrayList(Color.WHITE.toString(), Color.AQUA.toString());

	private HBox myMenuBar;
	
//	public SimulationMenuBarManager(){
//		this(null, null);
//		populateMenuBar();
//	}
//	
//	public SimulationMenuBarManager(String language){
//		this(null, null);
//		populateMenuBar();
//	}
	
	public SimulationMenuBarManager(SimulationMenuBarDelegate delegate){
		super(delegate);
		populateMenuBar();
	}
	
	public SimulationMenuBarManager(SimulationMenuBarDelegate delegate, String language){
		super(delegate);
		populateMenuBar();
	}
	
	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	//@Override
	Region getRegion() {
		return myMenuBar;
	}
	
	private void populateMenuBar() {
		if (myMenuBar == null) {
			myMenuBar = new HBox();
		}
		myMenuBar.getChildren().clear();

		Button Home = new Button("Home");
		Home.setOnMousePressed(event -> Home());
		myMenuBar.getChildren().add(Home);
		
		Text backgroundColor = new Text("Background Color:");
		backgroundColor.setId("text");
		myMenuBar.getChildren().add(backgroundColor);
		ColorPicker setBackgroundColor = new ColorPicker();
		setBackgroundColor.setStyle("-fx-color-label-visible: false ;");
		setBackgroundColor.setOnAction(event -> setBackgroundColor(setBackgroundColor.getValue()));

		myMenuBar.getChildren().add(setBackgroundColor);

		ChoiceBox<String> setTurtleImage = new ChoiceBox<String>(colors);
		myMenuBar.getChildren().add(setTurtleImage);

	}

	private void setBackgroundColor(Color color) {
		if (getDelegate() != null){
			getDelegate().setBackgroundColor(color);
		}
	}

	private void Home() {

	}

	/**
	 * Sets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with one or more of the
	 * Control components in the EditorMenuBar.
	 * 
	 * @param delegate
	 */
//	void setDelegate(SimulationMenuBarDelegate delegate){
//
//	}

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with one or more of the
	 * Control components in the EditorMenuBar.
	 * @return
	 * 		SimulationMenuBarDelegate
	 */
//	SimulationMenuBarDelegate getDelegate(){
//		return null;
//	}

	/**
	 * Changes the language that the menu bar uses to display its contents. The
	 * menu bar will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @param language
	 */
	void setLanguage(String language){

	}

	/**
	 * Gets the language that the menu bar uses to display its contents. The
	 * menu bar will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return
	 * 		String
	 */
	String getLanguage(){
		return null;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return
	 */
	Node getMenuBar(){
		return null;
	}
}
