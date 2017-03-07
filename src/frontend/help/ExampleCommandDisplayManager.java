/**
 * 
 */
package frontend.help;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import frontend.UIChild;
import frontend.UIChildDelegate;

/**
 * @author Dylan Peters
 *
 */
class ExampleCommandDisplayManager extends UIChild<UIChildDelegate> {
	private static final String DEFAULT_LANGUAGE_RESOURCE_PACKAGE = "resources.languages/";
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String DEFAULT_EXAMPLE_RESOURCE = "ExampleCommands";
	private static final String COMMENT_DELIMITER = "#";
	private static final String SPLIT_REGEX = " ";
	private static final String OR_DELIMITER = "\\|";

	private ScrollPane scrollPane;
	private VBox vbox;
	private ResourceBundle exampleCommandsResource;

	ExampleCommandDisplayManager() {
		this(ResourceBundle.getBundle(DEFAULT_LANGUAGE_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE));
	}

	ExampleCommandDisplayManager(ResourceBundle language) {
		exampleCommandsResource = ResourceBundle
				.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_EXAMPLE_RESOURCE);
		vbox = new VBox();
		scrollPane = new ScrollPane();
		vbox.setPadding(new Insets(10));
		populateVBox(language);
		scrollPane.setContent(vbox);
	}

	@Override
	public void setLanguageResourceBundle(ResourceBundle language) {
		populateVBox(language);
	}

	@Override
	public Region getRegion() {
		return scrollPane;
	}

	private void populateVBox(ResourceBundle language) {
		vbox.getChildren().clear();
		Label title = new Label(language.getString("ExampleCommands"));
		vbox.getChildren().add(title);
		initCommands(language);
	}

	private void initCommands(ResourceBundle language) {
		for (String key : exampleCommandsResource.keySet()) {
			addCommand(key, exampleCommandsResource.getString(key), language);
		}
	}

	private void addCommand(String name, String command, ResourceBundle language) {
		TextArea textArea = new TextArea(command);
		textArea.setEditable(false);
		vbox.getChildren().add(textArea);

		StringBuilder sb = new StringBuilder();
		sb.append(COMMENT_DELIMITER + " " + language.getString(name) + "\n");
		String[] splitCommand = command.split(SPLIT_REGEX);
		for (String key : splitCommand) {
			try {
				String commandInLang = language.getString(key);
				sb.append(commandInLang.split(OR_DELIMITER)[0].trim());
			} catch (MissingResourceException e) {
				sb.append(key);
			}
			sb.append(" ");
		}
		textArea.setText(sb.toString().trim());
		textArea.setWrapText(true);
	}

}
