package frontend.help;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import frontend.UIChild;
import frontend.UIChildDelegate;

public class HelpPaneManager extends UIChild<UIChildDelegate> {
	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 600;

	private static final String DEFAULT_STYLE_SHEET = "resources/darktheme.css";
	private static final String HELP_PAGE = "http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands.php";

	private BorderPane borderPane;

	private HTMLDisplayManager htmlDisplayManager;
	private URLBarManager urlBarManager;

	public HelpPaneManager() {
		initialize();
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
		return borderPane;
	}

	public void setStyleSheet(String styleSheet) {
		borderPane.getStylesheets().clear();
		borderPane.getStylesheets().add(styleSheet);
	}

	private void initialize() {

		borderPane = new BorderPane();

		urlBarManager = new URLBarManager(HELP_PAGE);
		htmlDisplayManager = new HTMLDisplayManager(HELP_PAGE);

		borderPane.setTop(urlBarManager.getRegion());
		borderPane.setBottom(htmlDisplayManager.getRegion());

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
	}

}
