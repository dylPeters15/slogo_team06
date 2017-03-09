package frontend.help;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import frontend.EmptyDelegate;
import frontend.SlogoBaseUIManager;

class URLBarManager extends SlogoBaseUIManager<EmptyDelegate, Region> {

	private HBox myMenuBar;
	private TextField myURLDisplay;

	URLBarManager(String url) {
		initialize(url);
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

	private void initialize(String url) {
		myMenuBar = new HBox();
		myURLDisplay = new TextField();

		HBox.setHgrow(myURLDisplay, Priority.ALWAYS);
		myURLDisplay.setPrefColumnCount(50);
		myURLDisplay.setText(url);
		myURLDisplay.setEditable(false);
		myMenuBar.getChildren().add(myURLDisplay);

		myMenuBar.prefHeightProperty().bind(myURLDisplay.heightProperty());

		myURLDisplay.prefWidthProperty().bind(myMenuBar.widthProperty());
	}

	@Override
	public EmptyDelegate createNonActiveDelegate() {
		return new EmptyDelegate() {
		};
	}

}
