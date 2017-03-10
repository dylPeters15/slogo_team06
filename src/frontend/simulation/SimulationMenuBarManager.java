package frontend.simulation;

import java.io.File;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import frontend.SlogoBaseUIManager;

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
class SimulationMenuBarManager extends SlogoBaseUIManager<Parent> {

	private HBox myMenuBar;
	private Button Home, setTurtleImage;
	private Text backgroundColorText, penColorText;
	private ColorPicker setBackgroundColor, setPenColor;

	private ObjectProperty<Color> penColor;
	private ObjectProperty<Color> backgroundColor;
	private ObjectProperty<Image> turtleImage;
	private BooleanProperty home;

	public SimulationMenuBarManager() {
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> arg0,
					ResourceBundle arg1, ResourceBundle arg2) {
				populateMenuBar();
			}
		});
		populateMenuBar();
	}

	public ObjectProperty<Color> getPenColor() {
		return penColor;
	}

	public ObjectProperty<Color> getBackgroundColor() {
		return backgroundColor;
	}

	public ObjectProperty<Image> getTurtleImage() {
		return turtleImage;
	}

	public BooleanProperty getHome() {
		return home;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	@Override
	public Region getObject() {
		return myMenuBar;
	}

	private void populateMenuBar() {
		penColor = new SimpleObjectProperty<Color>();
		backgroundColor = new SimpleObjectProperty<Color>();
		turtleImage = new SimpleObjectProperty<Image>();
		home = new SimpleBooleanProperty(false);

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
		penColorText = new Text("Pen Color:");
		backgroundColorText.setId("text");
		myMenuBar.getChildren().add(penColorText);

		setPenColor = new ColorPicker(Color.BLACK);
		setPenColor.setStyle("-fx-color-label-visible: false ;");
		setPenColor.setOnAction(event -> penColor.setValue(setPenColor
				.getValue()));
		myMenuBar.getChildren().add(setPenColor);
	}

	private void makeBackgroundColor() {
		backgroundColorText = new Text("Background Color:");
		backgroundColorText.setId("text");
		myMenuBar.getChildren().add(backgroundColorText);

		setBackgroundColor = new ColorPicker();
		setBackgroundColor.setStyle("-fx-color-label-visible: false ;");
		setBackgroundColor.setOnAction(event -> backgroundColor
				.setValue(setBackgroundColor.getValue()));
		myMenuBar.getChildren().add(setBackgroundColor);
	}

	private void makeHome() {
		Home = new Button("Home");
		Home.setOnMousePressed(event -> {
			home.setValue(true);
			home.setValue(false);
		});
		myMenuBar.getChildren().add(Home);
	}

	private void makeMenuBar() {
		if (myMenuBar == null) {
			myMenuBar = new HBox();
		}
		myMenuBar.getChildren().clear();
	}

	private void setTurtleImage() {
		FileChooser choose = new FileChooser();
		choose.setInitialDirectory(new File(System.getProperty("user.dir")));
		choose.getExtensionFilters().setAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File file = choose.showOpenDialog(null);
		if (file != null) {
			Image image = new Image(file.toURI().toString());
			turtleImage.setValue(image);
		}
	}

}
