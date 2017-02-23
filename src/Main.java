import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.editor.EditorPaneManager;

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
		stage.setScene(new Scene(new EditorPaneManager().getParent()));
		stage.setTitle(TITLE);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
