package frontend.help;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import frontend.EmptyDelegate;
import frontend.SlogoBaseUIManager;

class HTMLDisplayManager extends SlogoBaseUIManager<EmptyDelegate, Region> {

	private VBox myDisplay;
	private WebView browser;
	private WebEngine webEngine;

	HTMLDisplayManager(String url) {
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
		return myDisplay;
	}

	private void initialize(String url) {
		myDisplay = new VBox();
		browser = new WebView();
		VBox.setVgrow(browser, Priority.ALWAYS);
		webEngine = browser.getEngine();

		webEngine.load(url);

		myDisplay.getChildren().add(browser);

		browser.prefHeightProperty().bind(myDisplay.heightProperty());
		browser.prefWidthProperty().bind(myDisplay.widthProperty());
	}

	@Override
	public EmptyDelegate createNonActiveDelegate() {
		return new EmptyDelegate() {
		};
	}
}
