/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import frontend.SlogoBaseUIManager;
import frontend.help.HelpPaneManager;

/**
 * This class sets up and manages a menu bar to be used in the
 * EditorPaneManager. The goal of this class is to hide the details of
 * implementation required to display buttons and other Control objects required
 * for the user to interact with in the editor. It uses the styleSheet and
 * languageProperties that it inherits from the SlogoBaseUIManager to
 * communicate with other classes in the following way:
 * 
 * 1) Other classes set up listeners that listen to the language and stylesheet
 * properties of this class.
 * 
 * @author Dylan Peters
 *
 */
class EditorMenuBarManager extends SlogoBaseUIManager<Parent> {

	private HBox myMenuBar;

	private Stage helpPaneStage;
	private HelpPaneManager helpPaneManager;

	/**
	 * Creates a new instance of EditorMenuBarManager. Sets all values to
	 * default.
	 */
	public EditorMenuBarManager() {
		myMenuBar = new HBox();
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(
					ObservableValue<? extends ResourceBundle> observable,
					ResourceBundle oldLanguage, ResourceBundle newLanguage) {
				populateMenuBar();
			}
		});
		populateMenuBar();
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Parent returned by this method should be displayed to allow the user
	 * to interact with the editor and access all its options.
	 * 
	 * @return Parent containing all the Control components that allow the user
	 *         to interact with the program's options
	 */
	@Override
	public Parent getObject() {
		return myMenuBar;
	}

	/**
	 * Should be called when the stage containing the menu bar is closing. This
	 * method notifies the menu bar that it should close any extra windows that
	 * it has opened to display extra information to the user.
	 */
	public void closeAllChildWindows() {
		helpPaneStage.close();
	}

	private void populateMenuBar() {
		myMenuBar.getChildren().clear();

		ComboBox<String> selectLanguage = new ComboBox<String>(FXCollections
				.observableArrayList(
						getPossibleResourceBundleNamesAndResourceBundles()
								.keySet()).sorted());
		selectLanguage.setValue(getLanguage().getValue().getString("Language"));

		selectLanguage.setOnAction(event -> getLanguage().setValue(
				getPossibleResourceBundleNamesAndResourceBundles().get(
						selectLanguage.getValue())));
		myMenuBar.getChildren().add(selectLanguage);

		ObservableList<String> styles = FXCollections
				.observableArrayList(getPossibleStyleSheetNamesAndFileNames()
						.keySet());
		ComboBox<String> styleSheetSelector = new ComboBox<String>(styles);
		if (styles.size() > 0) {
			styleSheetSelector.setValue(styles.get(0));
		}
		styleSheetSelector.setOnAction(event -> getStyleSheet().setValue(
				getPossibleStyleSheetNamesAndFileNames().get(
						styleSheetSelector.getValue())));
		myMenuBar.getChildren().add(styleSheetSelector);

		Button help = new Button(getLanguage().getValue().getString("Help"));
		help.setOnMousePressed(event -> help());
		myMenuBar.getChildren().add(help);
		helpPaneStage = new Stage();

		helpPaneManager = new HelpPaneManager();
		helpPaneStage.setScene(new Scene(helpPaneManager.getObject()));
		helpPaneManager.getStyleSheet().bind(getStyleSheet());
		helpPaneManager.getLanguage().bind(getLanguage());
	}

	private void help() {
		helpPaneStage.show();
		helpPaneStage.toFront();
	}

}
