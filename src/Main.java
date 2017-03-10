import javafx.application.Application;
import javafx.stage.Stage;
import frontend.TabbedSlogoView;

/**
 * 
 */

/**
 * The Main class used to start the Slogo Application. This class extends the
 * JavaFX Application class in order to create a fully functional Java
 * application.
 * 
 * @author Dylan Peters
 *
 */
public class Main extends Application {

	/**
	 * The start method called when the Application launches. This method is the
	 * point at which the program sets the Application to be a Slogo
	 * Application.
	 * 
	 * @param Stage
	 *            stage the Stage to use as the base window for the Slogo
	 *            Application.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		new TabbedSlogoView(stage);
	}

	/**
	 * The main method used to start the program. It launches the Slogo
	 * Application.
	 * 
	 * @param args
	 *            An array of Strings representing the command line arguments
	 *            passed to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
