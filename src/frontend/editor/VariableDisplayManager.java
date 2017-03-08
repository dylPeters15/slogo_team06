/**
 * 
 */
package frontend.editor;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextAlignment;
import frontend.SlogoBaseUIManager;

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
class VariableDisplayManager extends
		SlogoBaseUIManager<VariableDisplayDelegate, Parent> {

	private TableColumn<Variable, String> names;
	private TableColumn<Variable, String> values;
	private TableView<Variable> table;
	private ObservableList<Variable> variables;

	private ObservableMap<String, String> varMap;

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
	// VariableDisplayManager(VariableDisplayDelegate delegate,
	// ResourceBundle language, ObservableMap<String, String> variableMap) {
	// initializeTable();
	// this.varMap = variableMap;
	// variableMap.addListener(new MapChangeListener<String, String>() {
	// @Override
	// public void onChanged(
	// javafx.collections.MapChangeListener.Change<? extends String, ? extends
	// String> arg0) {
	// update();
	// }
	// });
	// }

	VariableDisplayManager(ObservableMap<String, String> variableMap) {
		initializeTable();
		this.varMap = variableMap;
		variableMap.addListener(new MapChangeListener<String, String>() {
			@Override
			public void onChanged(
					javafx.collections.MapChangeListener.Change<? extends String, ? extends String> arg0) {
				update();
			}
		});
	}

	/**
	 * Sets the language that this class uses to display its contents. It will
	 * use a resource file with the words in that language to populate its
	 * contents.
	 * 
	 * @param language
	 *            a string representing the language to be displayed
	 */
	@Override
	public void languageResourceBundleDidChange() {
		names.setText(getLanguageResourceBundle().getString("Name"));
		values.setText(getLanguageResourceBundle().getString("Value"));
		Label placeHolder = new Label(getLanguageResourceBundle().getString(
				"TablePlaceholder"));
		placeHolder.setWrapText(true);
		placeHolder.setTextAlignment(TextAlignment.CENTER);
		table.setPlaceholder(placeHolder);

	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Node containing all the UI components that allow the user to
	 *         interact with the program
	 */
	@Override
	public Parent getObject() {
		return table;
	}

	/**
	 * Updates the variable display based on the variable map passed to it.
	 * 
	 * @param vars
	 *            a map whose keyset is the names of all the variables, and
	 *            whose values are the values of the variables.
	 */
	void update() {
		for (String varName : varMap.keySet()) {
			if (varListHasVarWithName(varName)) {
				variables.get(indexOfVarWithName(varName)).valueProperty()
						.set(varMap.get(varName));
			} else {
				variables.add(new Variable(varName, varMap.get(varName),
						isNumber(varMap.get(varName))));
			}
		}
		for (Variable var : variables) {
			if (!varMapContainsVarWithName(var.nameProperty().get())) {
				variables.remove(var);
			}
		}
		variables.sort(null);
	}

	private boolean isNumber(String var) {
		try {
			Double.parseDouble(var);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
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

	private boolean varMapContainsVarWithName(String varName) {
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
				"value"));
		values.setCellFactory(TextFieldTableCell.forTableColumn());
		values.setOnEditCommit(new EventHandler<CellEditEvent<Variable, String>>() {
			@Override
			public void handle(CellEditEvent<Variable, String> event) {
				Variable varChanged = variables.get(event.getTablePosition()
						.getRow());
				if (varChanged.isNumber()) {
					if (isNumber(event.getNewValue())) {
						varChanged.valueProperty().set(event.getNewValue());
						varMap.put(varChanged.nameProperty().get(),
								event.getNewValue());
					} else {
						variables.remove(varChanged);
						variables.add(varChanged);
						variables.sort(null);
					}
				} else {
					if (isNumber(event.getNewValue())) {
						variables.remove(varChanged);
						variables.add(varChanged);
						variables.sort(null);
					} else {
						varChanged.valueProperty().set(event.getNewValue());
						varMap.put(varChanged.nameProperty().get(),
								event.getNewValue());
					}
				}
			}
		});

		table.getColumns().add(values);

		table.setEditable(true);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	@Override
	public VariableDisplayDelegate createNonActiveDelegate() {
		return new VariableDisplayDelegate() {

			@Override
			public void didChangeVariable(String name, Object value) {

			}
		};
	}

	@Override
	protected void styleSheetDidChange() {
		table.getStylesheets().clear();
		table.getStylesheets().add(getStyleSheet());
	}

}