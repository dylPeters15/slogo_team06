/**
 * 
 */
package frontend.editor;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * This class sets up and manages a table to be used in the EditorPaneManager.
 * The goal of this class is to hide the details of implementation required to
 * display the names and values of all the variables currently in use in the
 * simulation.
 * 
 * @author Dylan Peters
 *
 */
class VariableDisplayManager extends SlogoBaseUIManager<Parent> {

	private TableColumn<Variable, String> names;
	private TableColumn<Variable, String> values;
	private TableView<Variable> table;
	private ObservableList<Variable> variableList;

	private ObservableMap<String, String> varMap;

	/**
	 * Creates a new instance of VariableDisplayManager. Sets all values for
	 * stylesheet and language to default values.
	 */
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
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(
					ObservableValue<? extends ResourceBundle> observable,
					ResourceBundle oldValue, ResourceBundle newValue) {
				languageResourceBundleDidChange();
			}
		});
		languageResourceBundleDidChange();
	}

	/**
	 * Gets the display object that this class is manipulating and setting up.
	 * The Node returned by this method should be displayed to allow the user to
	 * interact with the editor.
	 * 
	 * @return Parent containing all the UI components that allow the user to
	 *         interact with the program
	 */
	@Override
	public Parent getObject() {
		return table;
	}

	/**
	 * Updates the variable display based on the variable map passed to it.
	 */
	private void update() {
		for (String varName : varMap.keySet()) {
			if (varListHasVarWithName(varName)) {
				variableList.get(indexOfVarWithName(varName)).valueProperty()
						.set(varMap.get(varName));
			} else {
				variableList.add(new Variable(varName, varMap.get(varName),
						isNumber(varMap.get(varName))));
			}
		}
		for (Variable var : variableList) {
			if (!varMapContainsVarWithName(var.nameProperty().get())) {
				variableList.remove(var);
			}
		}
		variableList.sort(null);
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
		return indexOfVarWithName(varName) != -1;
	}

	private int indexOfVarWithName(String varName) {
		int index = 0;
		for (Variable var : variableList) {
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
		variableList = FXCollections.observableArrayList();
		table = new TableView<Variable>(variableList);
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
				Variable varChanged = variableList.get(event.getTablePosition()
						.getRow());
				if (varChanged.isNumber()) {
					if (isNumber(event.getNewValue())) {
						setVar(varChanged, event.getNewValue());
					} else {
						resetVar(varChanged);
					}
				} else {
					if (isNumber(event.getNewValue())) {
						resetVar(varChanged);
					} else {
						setVar(varChanged, event.getNewValue());
					}
				}
			}
		});

		table.getColumns().add(values);

		table.setEditable(true);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	private void setVar(Variable varChanged, String newValue) {
		varChanged.valueProperty().set(newValue);
		varMap.put(varChanged.nameProperty().get(), newValue);
	}

	private void resetVar(Variable varChanged) {
		variableList.remove(varChanged);
		variableList.add(varChanged);
		variableList.sort(null);
	}

	private void languageResourceBundleDidChange() {
		names.setText(getLanguage().getValue().getString("Name"));
		values.setText(getLanguage().getValue().getString("Value"));
		Label placeHolder = new Label(getLanguage().getValue().getString(
				"TablePlaceholder"));
		placeHolder.setWrapText(true);
		placeHolder.setTextAlignment(TextAlignment.CENTER);
		table.setPlaceholder(placeHolder);

	}

}