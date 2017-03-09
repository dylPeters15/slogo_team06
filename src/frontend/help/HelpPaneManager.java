package frontend.help;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import frontend.SlogoBaseUIManager;

public class HelpPaneManager extends SlogoBaseUIManager<Region> {
	private static final String HELP_PAGE = "http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands.php";

	private SplitPane split;

	private HTMLDisplayManager htmlDisplayManager;
	private URLBarManager urlBarManager;

	private ExampleCommandDisplayManager exampleCommandDisplayManager;

	public HelpPaneManager() {
		initialize();
		setStyleSheet(getStyleSheet());
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
	public Region getObject() {
		return split;
	}

	@Override
	public void styleSheetDidChange() {
		split.getStylesheets().clear();
		split.getStylesheets().add(getStyleSheet());
	}

	@Override
	public void languageResourceBundleDidChange() {
		htmlDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
		urlBarManager.setLanguageResourceBundle(getLanguageResourceBundle());
		exampleCommandDisplayManager
				.setLanguageResourceBundle(getLanguageResourceBundle());
	}

	private void initialize() {

		split = new SplitPane();
		split.setOrientation(Orientation.HORIZONTAL);

		BorderPane borderPane = new BorderPane();

		urlBarManager = new URLBarManager(HELP_PAGE);
		htmlDisplayManager = new HTMLDisplayManager(HELP_PAGE);

		borderPane.setTop(urlBarManager.getObject());
		borderPane.setCenter(htmlDisplayManager.getObject());

		urlBarManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());
		htmlDisplayManager
				.getObject()
				.prefHeightProperty()
				.bind(borderPane.heightProperty().subtract(
						urlBarManager.getObject().heightProperty()));
		htmlDisplayManager.getObject().prefWidthProperty()
				.bind(borderPane.widthProperty());

		split.getItems().add(borderPane);
		exampleCommandDisplayManager = new ExampleCommandDisplayManager();
		split.getItems().add(exampleCommandDisplayManager.getObject());
	}

}
