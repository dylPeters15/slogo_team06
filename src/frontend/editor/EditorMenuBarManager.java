/**
 * 
 */
package frontend.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

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
class EditorMenuBarManager extends EditorPaneManagerChild<EditorMenuBarDelegate> {
	private ObservableList<String> languages = FXCollections
			.observableArrayList("Zhōngwén", "English", "Français", "Deutsche",
					"Italiano", "Português", "Russkiy", "Español");

	private Map<String, String> styleMap;

	private HBox myMenuBar;

	/**
	 * Creates a new instance of EditorMenuBarManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language to use in the display of the menu bar.
	 */
	EditorMenuBarManager(ResourceBundle language) {
		this(null, language);
	}

	/**
	 * Creates a new instance of EditorMenuBarManager. Sets all values except
	 * delegate and language to default.
	 * 
	 * @param delegate
	 *            the object implementing the EditorMenuBarDelegate interface
	 *            that this class will use to call delegated methods.
	 * @param language
	 *            the language to use in the display of the menu bar.
	 */
	EditorMenuBarManager(EditorMenuBarDelegate delegate, ResourceBundle language) {
		super(delegate, language);
		populateMenuBar(language);
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
	Region getRegion() {
		return myMenuBar;
	}

	@Override
	void setLanguageResourceBundle(ResourceBundle language) {
		populateMenuBar(language);
	}

	private void populateMenuBar(ResourceBundle language) {
		if (myMenuBar == null) {
			myMenuBar = new HBox();
		}
		myMenuBar.getChildren().clear();

		Button seeUserDefinedCommands = new Button(
				language.getString("SeeUserCommands"));
		seeUserDefinedCommands
				.setOnMousePressed(event -> seeUserDefinedCommands());
		myMenuBar.getChildren().add(seeUserDefinedCommands);

		ComboBox<String> selectLanguage = new ComboBox<String>(languages);
		selectLanguage.setValue(language.getString("Language"));

		selectLanguage.setOnAction(event -> didSelectLanguage(selectLanguage
				.getValue()));
		myMenuBar.getChildren().add(selectLanguage);

		Button help = new Button(language.getString("Help"));
		help.setOnMousePressed(event -> help());
		myMenuBar.getChildren().add(help);

		populateStyleMap(language);
		ObservableList<String> styles = FXCollections.observableArrayList(styleMap.keySet());
		ComboBox<String> styleSheetSelector = new ComboBox<String>(styles);
		if (styles.size() > 0) {
			styleSheetSelector.setValue(styles.get(0));
		}
		styleSheetSelector
				.setOnAction(event -> setStyleSheet(styleSheetSelector
						.getValue()));
		myMenuBar.getChildren().add(styleSheetSelector);

	}

	private void setStyleSheet(String styleSheet) {
		if (getDelegate() != null && getDelegate() instanceof EditorMenuBarDelegate) {
			((EditorMenuBarDelegate)getDelegate()).setStyleSheet(styleMap.get(styleSheet));
		}
	}

	private void seeUserDefinedCommands() {
		if (getDelegate() != null) {
			getDelegate().seeUserDefinedCommands();
		}
	}

	private void didSelectLanguage(String language) {
		if (getDelegate() != null) {
			getDelegate().didSelectLanguage(language);
		}
	}

	private void help() {
		if (getDelegate() != null) {
			getDelegate().help();
		}
	}

	private void populateStyleMap(ResourceBundle language) {
		styleMap = new HashMap<String, String>();
		styleMap.put(language.getString("DefaultTheme"), "resources/default.css");
		styleMap.put(language.getString("DarkTheme"), "resources/darktheme.css");
	}

}
