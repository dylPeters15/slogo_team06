/**
 * 
 */
package frontend.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * This class will be of default visibility, so it will only be visible to other
 * members of its package. Therefore, it will be part of the internal API of the
 * front end.
 * 
 * This class sets up and manages a table to be used in the EditorPaneManager.
 * The goal of this class is to hide the details of implementation required to
 * display the names and values of all the variables currently in use in the
 * simulation. This class can hold an object that implements the
 * VariableDisplayManager interface. It will call the methods of that interface
 * when a variable is changed by the user, to alert the rest of the program
 * about the changes.
 * 
 * @author Dylan Peters
 *
 */
class VariableDisplayManager {
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";

	private Map<String, String> languageToPropertyName = new HashMap<String, String>();

	private ResourceBundle myResources;
	private String language;

	private VariableDisplayDelegate delegate;

	private TableColumn<Variable, String> names;
	private TableColumn<Variable, String> values;
	private TableView<Variable> table;
	private ObservableList<Variable> variables;

	/**
	 * Creates a new instance of VariableDisplayManager. Sets all values to
	 * default.
	 */
	VariableDisplayManager() {
		this(DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of VariableDisplayManager. Sets all values except
	 * language to default.
	 * 
	 * @param language
	 *            the language with which to display the text in the variable
	 *            display.
	 */
	VariableDisplayManager(String language) {
		this(null, language);
	}

	/**
	 * Creates a new instance of VariableDisplayManager. Sets all values except
	 * delegate to default.
	 * 
	 * @param delegate
	 *            the object implementing the VariableDisplayDelegate interface
	 *            that this class will use to call delegated methods.
	 */
	VariableDisplayManager(VariableDisplayDelegate delegate) {
		this(delegate, DEFAULT_LANGUAGE);
	}

	/**
	 * Creates a new instance of VariableDisplayManager. Sets all values except
	 * delegate and language to default.
	 * 
	 * @param delegate
	 *            the object implementing the VariableDisplayDelegate interface
	 *            that this class will use to call delegated methods.
	 * @param language
	 *            the language with which to display the text in the variable
	 *            display.
	 */
	VariableDisplayManager(VariableDisplayDelegate delegate, String language) {
		initializeTable();
		setDelegate(delegate);
		populateLanguageMap();
		setLanguage(language);
	}

	/**
	 * Sets the language that this class uses to display its contents. It will
	 * use a resource file with the words in that language to populate its
	 * contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	void setLanguage(String language) {
		this.language = language;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ languageToPropertyName.get(language));
		if (names != null) {
			names.setText(myResources.getString("Name"));
		}

		if (values != null) {
			values.setText(myResources.getString("Value"));
		}

	}

	/**
	 * Gets the language that this class uses to display its contents. This
	 * class will use a resource file with the words in that language to
	 * populate its contents.
	 * 
	 * @return a string representing the language to be displayed
	 */
	String getLanguage() {
		return language;
	}

	/**
	 * Sets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the VariableDisplay.
	 * 
	 * @param delegate
	 *            the object implementing the VariableDisplayDelegate interface
	 *            that this class will use as its delegate
	 */
	void setDelegate(VariableDisplayDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * Gets the delegate of this instance to the object passed. The delegate's
	 * methods are called when the user interacts with the fields in the
	 * VariableDisplay.
	 * 
	 * @return the object implementing the VariableDisplayDelegate interface
	 *         that this class will use as its delegate
	 */
	VariableDisplayDelegate getDelegate() {
		return delegate;
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	Node getVariableDisplay() {
		return table;
	}

	/**
	 * Updates the variable display based on the variable map passed to it.
	 * 
	 * @param vars
	 *            a map whose keyset is the names of all the variables, and
	 *            whose values are the values of the variables.
	 */
	void update(Map<String, Object> varMap) {
		for (String varName : varMap.keySet()) {
			if (varListHasVarWithName(varName)) {
				variables.get(indexOfVarWithName(varName)).objectProperty()
						.set(varMap.get(varName));
			} else {
				variables.add(new Variable(varName, varMap.get(varName)));
			}
		}
		for (Variable var : variables) {
			if (!varMapContainsVarWithName(varMap, var.nameProperty().get())) {
				variables.remove(var);
			}
		}
		variables.sort(null);
	}

	private boolean varListHasVarWithName(String varName) {
		for (Variable var : variables) {
			if (var.nameProperty().get().equals(varName)) {
				return true;
			}
		}
		return false;
	}

	private int indexOfVarWithName(String varName) {
		int index = 0;
		for (Variable var : variables) {
			if (var.nameProperty().get().equals(varName)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	private boolean varMapContainsVarWithName(Map<String, Object> varMap,
			String varName) {
		return varMap.keySet().contains(varName);
	}

	private void initializeTable() {
		variables = FXCollections.observableArrayList();
		table = new TableView<Variable>(variables);
		names = new TableColumn<Variable, String>();
		names.setCellValueFactory(new PropertyValueFactory<Variable, String>(
				"name"));
		table.getColumns().add(names);

		values = new TableColumn<Variable, String>();
		values.setCellValueFactory(new PropertyValueFactory<Variable, String>(
				"valueRepresentation"));
		values.setCellFactory(TextFieldTableCell.forTableColumn());
		values.setOnEditCommit(new EventHandler<CellEditEvent<Variable, String>>() {
			@Override
			public void handle(CellEditEvent<Variable, String> event) {
				Variable varChanged = variables.get(event.getTablePosition()
						.getRow());
				try {
					if (varChanged.objectProperty().get() instanceof String) {
						varChanged.objectProperty().set(event.getNewValue());
					} else if (varChanged.objectProperty().get() instanceof Integer) {
						varChanged.objectProperty().set(
								Integer.parseInt(event.getNewValue()));
					} else if (varChanged.objectProperty().get() instanceof Float) {
						varChanged.objectProperty().set(
								Float.parseFloat(event.getNewValue()));
					} else if (varChanged.objectProperty().get() instanceof Double) {
						varChanged.objectProperty().set(
								Double.parseDouble(event.getNewValue()));
					}
				} catch (Exception e) {
					variables.remove(varChanged);
					variables.add(varChanged);
					variables.sort(null);
				}

			}
		});

		table.getColumns().add(values);
		variables.add(new Variable("asdf", 3));
		table.setEditable(true);
	}

	private void populateLanguageMap() {
		languageToPropertyName.put("Zhōngwén", "Chinese");
		languageToPropertyName.put("English", "English");
		languageToPropertyName.put("Français", "French");
		languageToPropertyName.put("Deutsche", "German");
		languageToPropertyName.put("Italiano", "Italian");
		languageToPropertyName.put("Português", "Portuguese");
		languageToPropertyName.put("Russkiy", "Russian");
		languageToPropertyName.put("Español", "Spanish");
	}

	public class Variable implements Comparable<Variable> {

		private StringProperty nameProperty;
		private ObjectProperty<Object> objectProperty;
		private StringProperty valueRepresentationProperty;

		Variable(String name) {
			this(name, null);
		}

		Variable(String name, Object value) {
			nameProperty = new SimpleStringProperty(name);
			objectProperty = new SimpleObjectProperty<>(value);
			valueRepresentationProperty = new SimpleStringProperty(
					objectProperty.get().toString());
			objectProperty.addListener(listener -> valueRepresentationProperty
					.set(objectProperty.get().toString()));
		}

		public StringProperty nameProperty() {
			return nameProperty;
		}

		public ObjectProperty<Object> objectProperty() {
			return objectProperty;
		}

		public StringProperty valueRepresentationProperty() {
			return valueRepresentationProperty;
		}

		@Override
		public int compareTo(Variable other) {
			return this.nameProperty().get()
					.compareTo(other.nameProperty().get());
		}

		public boolean equals(Variable other) {
			return this.nameProperty().get().equals(other.nameProperty().get());
		}

	}

}