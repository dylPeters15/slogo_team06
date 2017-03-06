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
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";
	private Stage editorStage;
	private Stage simulationStage;

	private TabPane editorTabPane;
	private TabPane simulationTabPane;

	private ObservableList<Workspace> workspaces;

	private int numWorkspacesThatHaveExisted;

	private ResourceBundle defaultLanguage;

	public SlogoController() {
		this(new Stage());
	}

	public SlogoController(Stage stage) {
		numWorkspacesThatHaveExisted = 0;
		defaultLanguage = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE);
		editorStage = stage;
		simulationStage = new Stage();

		editorTabPane = new TabPane();
		simulationTabPane = new TabPane();

		editorStage.setScene(new Scene(editorTabPane));
		simulationStage.setScene(new Scene(simulationTabPane));

		editorStage.setTitle(EDITOR_TITLE);
		simulationStage.setTitle(SIMULATOR_TITLE);

		workspaces = FXCollections.observableArrayList();
		workspaces.addListener(new ListChangeListener<Workspace>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Workspace> change) {
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
						} else if (!newTab.equals(addTab)) {
							simulationTabPane.getSelectionModel().select(
									editorTabPane.getSelectionModel()
											.getSelectedIndex());
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
						} else if (!newTab.equals(addTab)) {
							editorTabPane.getSelectionModel().select(
									simulationTabPane.getSelectionModel()
											.getSelectedIndex());
						}
					}
				});

		addWorkspace();

		editorStage.show();
		simulationStage.show();
	}

	private void pairsModified(
			ListChangeListener.Change<? extends Workspace> change) {
		if (change.next() && change.wasAdded()) {
			for (Workspace workspace : change.getAddedSubList()) {
				workspace.editorTab.setOnClosed(event -> remove(workspace));
				editorTabPane.getTabs().add(editorTabPane.getTabs().size() - 1,
						workspace.editorTab);

				workspace.simulationTab.setOnClosed(event -> remove(workspace));
				simulationTabPane.getTabs().add(
						simulationTabPane.getTabs().size() - 1,
						workspace.simulationTab);
			}
		} else if (change.wasRemoved()) {
			for (Workspace workspace : change.getRemoved()) {
				editorTabPane.getTabs().remove(workspace.editorTab);
				simulationTabPane.getTabs().remove(workspace.simulationTab);
			}
		}
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

	private void remove(Workspace workspace) {
		if (workspaces.contains(workspace)) {
			workspaces.remove(workspace);
		}
	}

	private void addWorkspace() {
		workspaces.add(new Workspace(this));
	}

	@Override
	public void didChangeLanguage(EditorPaneManager editor,
			ResourceBundle newLanguage) {
		for (Workspace workspace : workspaces) {
			if (workspace.editor.equals(editor)) {
				// workspace.simulation.setLanguage(newLanguage);
				workspace.editorTab
						.setText(newLanguage.getString("Workspace")
								+ " "
								+ workspace.editorTab.getText().substring(
										workspace.editorTab.getText().indexOf(
												" ") + 1));
				workspace.simulationTab.setText(newLanguage
						.getString("Workspace")
						+ " "
						+ workspace.simulationTab.getText()
								.substring(
										workspace.simulationTab.getText()
												.indexOf(" ") + 1));
			}
		}
	}

	@Override
	public void didChangeStylesheet(EditorPaneManager editor, String stylesheet) {
		for (Workspace workspace : workspaces) {
			if (workspace.editor.equals(editor)) {
				workspace.simulation.setStyleSheet(stylesheet);
			}
		}
	}

	class Workspace {
		Scene editorScene, simulationScene;
		EditorPaneManager editor;
		SimulationPaneManager simulation;
		Tab editorTab, simulationTab;
		Model model;

		public Workspace(EditorPaneManagerDelegate editorDelegate) {
			model = new Model();
			editor = new EditorPaneManager(editorDelegate, model);
			editor.setLanguage(defaultLanguage);
			simulation = new SimulationPaneManager(model.getStatesList());

			editorScene = new Scene(editor.getRegion());
			simulationScene = new Scene(simulation.getParent());
			editorTab = new Tab(defaultLanguage.getString("Workspace") + " "
					+ String.valueOf(numWorkspacesThatHaveExisted),
					editorScene.getRoot());
			simulationTab = new Tab(String.valueOf(defaultLanguage
					.getString("Workspace")
					+ " "
					+ numWorkspacesThatHaveExisted++),
					simulationScene.getRoot());
		}
	}

}
