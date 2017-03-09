import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.help.HelpPaneManager;

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
		//new SlogoController(stage);
		stage.setScene(new Scene(new HelpPaneManager().getObject()));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
