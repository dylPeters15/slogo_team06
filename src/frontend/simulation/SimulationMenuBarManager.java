package frontend.simulation;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

	private HBox myMenuBar;
	private Button Home,setTurtleImage;
	private Text backgroundColor, penColor;
	private ColorPicker setBackgroundColor, setPenColor;
	
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
	Region getRegion() {
		return myMenuBar;
	}
	
	private void populateMenuBar() {
		makeMenuBar();
		makeHome();		
		makeBackgroundColor();		
		makePenColor();
		makeTurtleImage();
		
		myMenuBar.prefHeightProperty().bind(Home.heightProperty());
	}

	private void makeTurtleImage() {
		setTurtleImage = new Button("Set Turtle Image");
		setTurtleImage.setOnMousePressed(event -> setTurtleImage());
		myMenuBar.getChildren().add(setTurtleImage);
	}

	private void makePenColor() {
		penColor = new Text("Pen Color:");
		backgroundColor.setId("text");
		myMenuBar.getChildren().add(penColor);
		
		setPenColor = new ColorPicker(Color.BLACK);
		setPenColor.setStyle("-fx-color-label-visible: false ;");
		setPenColor.setOnAction(event -> setPenColor(setPenColor.getValue()));
		myMenuBar.getChildren().add(setPenColor);
	}

	private void makeBackgroundColor() {
		backgroundColor = new Text("Background Color:");
		backgroundColor.setId("text");
		myMenuBar.getChildren().add(backgroundColor);
		
		setBackgroundColor = new ColorPicker();
		setBackgroundColor.setStyle("-fx-color-label-visible: false ;");
		setBackgroundColor.setOnAction(event -> setBackgroundColor(setBackgroundColor.getValue()));
		myMenuBar.getChildren().add(setBackgroundColor);
	}

	private void makeHome() {
		Home = new Button("Home");
		Home.setOnMousePressed(event -> Home());
		myMenuBar.getChildren().add(Home);
	}

	private void makeMenuBar() {
		if (myMenuBar == null) {
			myMenuBar = new HBox();
		}
		myMenuBar.getChildren().clear();
	}

	private void setPenColor(Color color) {
		if (getDelegate() != null){
			getDelegate().setPenColor(color);
		}
	}

	private void setTurtleImage() {
		FileChooser choose = new FileChooser();
		choose.setInitialDirectory(new File(System.getProperty("user.dir")));
		choose.getExtensionFilters().setAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File file = choose.showOpenDialog(null);
        if (file != null){
        	Image image = new Image(file.toURI().toString());
        	if (getDelegate() != null && image != null){
    			getDelegate().setTurtleImage(image);
    		}
        }
	}

	private void setBackgroundColor(Color color) {
		if (getDelegate() != null){
			getDelegate().setBackgroundColor(color);
		}
	}

	private void Home() {
		if (getDelegate() != null){
			getDelegate().home();
		}
	}

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
}
