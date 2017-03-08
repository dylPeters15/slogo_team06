import javafx.application.Application;
import javafx.stage.Stage;
import controller.SlogoController;

/**
 * 
 */

/**
 * @author Dylan Peters
 *
 */
public class Main extends Application {
	public static final String TITLE = "Slogo";

	@Override
	public void start(Stage stage) throws Exception {
		new SlogoController(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
