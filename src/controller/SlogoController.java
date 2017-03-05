/**
 * 
 */
package controller;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import backend.Model;
import frontend.editor.EditorPaneManager;
import frontend.editor.EditorPaneManagerDelegate;
import frontend.simulation.SimulationPaneManager;

/**
 * @author Dylan Peters
 *
 */
public class SlogoController implements EditorPaneManagerDelegate {
	private static final String EDITOR_TITLE = "Slogo!";
	private static final String SIMULATOR_TITLE = "Slogo!";
	private Stage editorStage;
	private Stage simulationStage;

	private TabPane editorTabPane;
	private TabPane simulationTabPane;

	private ObservableList<Pair<EditorPaneManager, SimulationPaneManager>> editorSimulationPairs;

	private int numWorkspaces;

	public SlogoController() {
		this(new Stage());
	}

	public SlogoController(Stage stage) {
		numWorkspaces = 0;
		editorStage = stage;
		simulationStage = new Stage();

		editorTabPane = new TabPane();
		simulationTabPane = new TabPane();

		editorStage.setScene(new Scene(editorTabPane));
		simulationStage.setScene(new Scene(simulationTabPane));

		editorStage.setTitle(EDITOR_TITLE);
		simulationStage.setTitle(SIMULATOR_TITLE);

		editorSimulationPairs = FXCollections.observableArrayList();
		editorSimulationPairs
				.addListener(new ListChangeListener<Pair<EditorPaneManager, SimulationPaneManager>>() {
					@Override
					public void onChanged(
							javafx.collections.ListChangeListener.Change<? extends Pair<EditorPaneManager, SimulationPaneManager>> change) {
						pairsModified(change);
					}
				});

		Tab addTab = new Tab("+");
		addTab.setClosable(false);
		editorTabPane.getTabs().add(addTab);
		simulationTabPane.getTabs().add(addTab);

		editorTabPane.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(
							ObservableValue<? extends Tab> observable,
							Tab oldTab, Tab newTab) {
						if (!oldTab.equals(addTab) && newTab.equals(addTab)) {
							addWorkspace();
						} else if (!newTab.equals(addTab)){
							simulationTabPane.getSelectionModel().select(editorTabPane.getSelectionModel().getSelectedIndex());
						}
					}
				});
		simulationTabPane.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(
							ObservableValue<? extends Tab> observable,
							Tab oldTab, Tab newTab) {
						if (!oldTab.equals(addTab) && newTab.equals(addTab)) {
							addWorkspace();
						} else if (!newTab.equals(addTab)){
							editorTabPane.getSelectionModel().select(simulationTabPane.getSelectionModel().getSelectedIndex());
						}
					}
				});

		addWorkspace();

		editorStage.show();
		simulationStage.show();
	}

	private void pairsModified(
			ListChangeListener.Change<? extends Pair<EditorPaneManager, SimulationPaneManager>> change) {
		if (change.next() && change.wasAdded()) {
			for (Pair<EditorPaneManager, SimulationPaneManager> pair : change
					.getAddedSubList()) {
				Tab editorTab = new Tab("Workspace " + numWorkspaces, pair
						.getKey().getParent());
				editorTab.setOnClosed(event -> remove(pair));
				editorTabPane.getTabs().add(editorTabPane.getTabs().size() - 1,
						editorTab);

				Tab simulationTab = new Tab("Workspace " + numWorkspaces++,
						pair.getValue().getParent());
				simulationTab.setOnClosed(event -> remove(pair));
				simulationTabPane.getTabs().add(
						simulationTabPane.getTabs().size() - 1, simulationTab);
			}
		} else if (change.wasRemoved()) {
			for (Pair<EditorPaneManager, SimulationPaneManager> pair : change
					.getRemoved()) {
				removeTabForEditorPane(pair.getKey());
				removeTabForSimulationPane(pair.getValue());
			}
		}
		for (int i = 0; i < editorTabPane.getTabs().size() - 2; i++) {
			editorTabPane.getTabs().get(i).setClosable(true);
			simulationTabPane.getTabs().get(i).setClosable(true);
		}
		editorTabPane.getTabs().get(editorTabPane.getTabs().size() - 2)
				.setClosable(true);
		simulationTabPane.getTabs().get(simulationTabPane.getTabs().size() - 2)
				.setClosable(true);

		editorTabPane
				.selectionModelProperty()
				.get()
				.select(editorTabPane.getTabs().get(
						editorTabPane.getTabs().size() - 2));
		simulationTabPane
				.selectionModelProperty()
				.get()
				.select(simulationTabPane.getTabs().get(
						simulationTabPane.getTabs().size() - 2));
	}

	private void removeTabForEditorPane(EditorPaneManager editor) {
		Tab toRemove = null;
		for (Tab editorTab : editorTabPane.getTabs()) {
			if (editorTab.getContent() != null
					&& editorTab.getContent().equals(editor.getParent())) {
				toRemove = editorTab;
			}
		}
		if (toRemove != null) {
			editorTabPane.getTabs().remove(toRemove);
		}
	}

	private void removeTabForSimulationPane(SimulationPaneManager sim) {
		Tab toRemove = null;
		for (Tab simulationTab : simulationTabPane.getTabs()) {
			if (simulationTab.getContent() != null
					&& simulationTab.getContent().equals(sim.getParent())) {
				toRemove = simulationTab;
			}
		}
		if (toRemove != null) {
			simulationTabPane.getTabs().remove(toRemove);
		}
	}

	private void remove(Pair<EditorPaneManager, SimulationPaneManager> pair) {
		if (editorSimulationPairs.contains(pair)) {
			editorSimulationPairs.remove(pair);
		}
	}

	private void addWorkspace() {
		Model model = new Model();
		EditorPaneManager editorPane = new EditorPaneManager(this,model);
		SimulationPaneManager simPane = new SimulationPaneManager(
				model.getStatesList());
		Pair<EditorPaneManager, SimulationPaneManager> pair = new Pair<EditorPaneManager, SimulationPaneManager>(
				editorPane, simPane);
		editorSimulationPairs.add(pair);
	}

	@Override
	public void didChangeLanguage(EditorPaneManager editor,
			ResourceBundle newLanguage) {
		for (Pair<EditorPaneManager, SimulationPaneManager> pair : editorSimulationPairs) {
			if (pair.getValue().equals(editor)) {
				// pair.getValue().setLanguage(newLanguage);
			}
		}
	}

	@Override
	public void didChangeStylesheet(EditorPaneManager editor, String stylesheet) {
		for (Pair<EditorPaneManager, SimulationPaneManager> pair : editorSimulationPairs) {
			if (pair.getKey().equals(editor)) {
				pair.getValue().setStyleSheet(stylesheet);
			}
		}
	}

}
