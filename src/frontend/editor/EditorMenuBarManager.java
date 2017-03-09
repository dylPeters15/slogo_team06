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
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a menu bar to be used in the
 * EditorPaneManager. The goal of this class is to hide the details of
 * implementation required to display buttons and other Control objects required
 * for the user to interact with in the editor. This class can hold an object
 * that implements the EditorMenuBarDelegate interface. It will call the methods
 * of that interface when Control components are changed by the user, to alert
 * the rest of the program about the changes.
 * 
 * @author Dylan Peters
 *
 */
class EditorMenuBarManager extends SlogoBaseUIManager<Parent> {

	private HBox myMenuBar;

	private Stage helpPaneStage;
	private HelpPaneManager helpPaneManager;

	/**
	 * Creates a new instance of EditorMenuBarManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language to use in the display of the menu bar.
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
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor and access all its options.
	 * 
	 * @return Node containing all the Control components that allow the user to
	 *         interact with the program's options
	 */
	@Override
	public Parent getObject() {
		return myMenuBar;
	}

	private void help() {
		helpPaneStage.show();
		helpPaneStage.toFront();
	}

	public void close() {
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

}
