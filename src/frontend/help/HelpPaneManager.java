package frontend.help;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HelpPaneManager {
	
	private static final String DEFAULT_STYLE_SHEET = "resources/darktheme.css";
	private static final String HELP_PAGE = "http://www.cs.duke.edu/courses/compsci308/spring17/assign/03_slogo/commands.php";
	
	private BorderPane borderPane;
	
	private HTMLDisplayManager htmlDisplayManager;
	private URLBarManager urlBarManager;
	
	public HelpPaneManager(){
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
	public Parent getParent() {
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
		
	}

}
