package frontend.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import frontend.SlogoBaseUIManager;

public class HelpPaneManager extends SlogoBaseUIManager<Region> {
	private static final List<String> URL_LIST = new ArrayList<String>(Arrays.asList(
			"http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands.php",
			"http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands2_J2W.php"
			));

	private SplitPane split;

	private TabbedHTMLDisplayManager tabbedhtmlDisplayManager;

	private ExampleCommandDisplayManager exampleCommandDisplayManager;

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
	public Region getObject() {
		return split;
	}

	private void initialize() {

		split = new SplitPane();
		split.setOrientation(Orientation.HORIZONTAL);

		tabbedhtmlDisplayManager = new TabbedHTMLDisplayManager(URL_LIST);
		
		tabbedhtmlDisplayManager
				.getObject()
				.prefHeightProperty()
				.bind(split.heightProperty());

		split.getItems().add(tabbedhtmlDisplayManager.getObject());
		
		exampleCommandDisplayManager = new ExampleCommandDisplayManager();
		split.getItems().add(exampleCommandDisplayManager.getObject());
		
		tabbedhtmlDisplayManager.getLanguage().bind(getLanguage());
		tabbedhtmlDisplayManager.getStyleSheet().bind(getStyleSheet());
		exampleCommandDisplayManager.getLanguage().bind(getLanguage());
		exampleCommandDisplayManager.getStyleSheet().bind(getStyleSheet());
	}

}
