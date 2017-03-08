package frontend.help;

import java.util.ResourceBundle;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import frontend.PlaceHolder;
import frontend.EmptyDelegate;

public class HelpPaneManager extends PlaceHolder<EmptyDelegate> {
	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 600;

	private static final String DEFAULT_STYLE_SHEET = "resources/default.css";
	private static final String HELP_PAGE = "http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands.php";

	private SplitPane split;

	private HTMLDisplayManager htmlDisplayManager;
	private URLBarManager urlBarManager;

	private ExampleCommandDisplayManager exampleCommandDisplayManager;

	public HelpPaneManager() {
		this(null);
	}

	public HelpPaneManager(ResourceBundle language) {
		initialize();
		setLanguageResourceBundle(language);
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor. It can be used as the root of a Scene or
	 * added as a component in a larger display.
	 * 
	 * @return Parent containing all the UI components that allow the user to
	 *         interact with the Editor portion of the program
	 */
	@Override
	public Region getRegion() {
		return split;
	}

	public void setStyleSheet(String styleSheet) {
		split.getStylesheets().clear();
		split.getStylesheets().add(styleSheet);
	}

	@Override
	public void setLanguageResourceBundle(ResourceBundle language) {
		if (language != null && exampleCommandDisplayManager != null) {
			exampleCommandDisplayManager.setLanguageResourceBundle(language);
		}
	}

	private void initialize() {

		split = new SplitPane();
		split.setOrientation(Orientation.HORIZONTAL);

		BorderPane borderPane = new BorderPane();

		urlBarManager = new URLBarManager(HELP_PAGE);
		htmlDisplayManager = new HTMLDisplayManager(HELP_PAGE);

		borderPane.setTop(urlBarManager.getRegion());
		borderPane.setCenter(htmlDisplayManager.getRegion());

		setStyleSheet(DEFAULT_STYLE_SHEET);

		urlBarManager.getRegion().prefWidthProperty()
				.bind(borderPane.widthProperty());
		htmlDisplayManager
				.getRegion()
				.prefHeightProperty()
				.bind(borderPane.heightProperty().subtract(
						urlBarManager.getRegion().heightProperty()));
		htmlDisplayManager.getRegion().prefWidthProperty()
				.bind(borderPane.widthProperty());

		borderPane.setPrefWidth(DEFAULT_WIDTH);
		borderPane.setPrefHeight(DEFAULT_HEIGHT);

		split.getItems().add(borderPane);
		exampleCommandDisplayManager = new ExampleCommandDisplayManager();
		split.getItems().add(exampleCommandDisplayManager.getRegion());
	}

}
