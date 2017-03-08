/**
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;
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

/**
 * @author Dylan Peters
 *
 */
public class SlogoController implements WorkspaceDelegate {
	private static final String EDITOR_TITLE = "Slogo!";
	private static final String SIMULATOR_TITLE = "Slogo!";
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";
	private Stage editorStage;
	private Stage simulationStage;

	private TabPane editorTabPane;
	private TabPane simulationTabPane;

	private ObservableList<Workspace> workspaces;
	private Map<Workspace, Pair<Tab, Tab>> workspaceToTabs;

	private int numWorkspacesThatHaveExisted;

	private ResourceBundle language;

	public SlogoController() {
		this(new Stage());
	}

	public SlogoController(Stage stage) {
		language = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE);
		numWorkspacesThatHaveExisted = 0;
		workspaceToTabs = new HashMap<Workspace, Pair<Tab, Tab>>();

		editorStage = stage;
		simulationStage = new Stage();

		editorStage.setOnCloseRequest(event -> requestAllClose());
		simulationStage.setOnCloseRequest(event -> requestAllClose());

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
				workspacesModified(change);
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

	private void requestAllClose() {
		for (Workspace workspace : workspaces) {
			workspace.close();
		}
		editorStage.close();
		simulationStage.close();
	}

	private void workspacesModified(
			ListChangeListener.Change<? extends Workspace> change) {
		if (change.next() && change.wasAdded()) {
			for (Workspace workspace : change.getAddedSubList()) {
				workspaceToTabs.put(
						workspace,
						new Pair<Tab, Tab>(new Tab(language
								.getString("Workspace")
								+ " "
								+ numWorkspacesThatHaveExisted, workspace
								.getEditorRegion()), new Tab(language
								.getString("Workspace")
								+ " "
								+ numWorkspacesThatHaveExisted++, workspace
								.getSimulationRegion())));
				workspaceToTabs.get(workspace).getKey()
						.setOnClosed(event -> workspaces.remove(workspace));
				editorTabPane.getTabs().add(editorTabPane.getTabs().size() - 1,
						workspaceToTabs.get(workspace).getKey());

				workspaceToTabs.get(workspace).getValue()
						.setOnClosed(event -> workspaces.remove(workspace));
				simulationTabPane.getTabs().add(
						simulationTabPane.getTabs().size() - 1,
						workspaceToTabs.get(workspace).getValue());
			}
		} else if (change.wasRemoved()) {
			for (Workspace workspace : change.getRemoved()) {
				editorTabPane.getTabs().remove(
						workspaceToTabs.get(workspace).getKey());
				simulationTabPane.getTabs().remove(
						workspaceToTabs.get(workspace).getValue());
			}
		}
		setClosingPolicy();
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

	private void setClosingPolicy() {
		if (editorTabPane.getTabs().size() == 2) {
			for (Tab tab : editorTabPane.getTabs()) {
				tab.setClosable(false);
			}
			for (Tab tab : simulationTabPane.getTabs()) {
				tab.setClosable(false);
			}
		} else {
			for (int i = 0; i < editorTabPane.getTabs().size() - 1; i++) {
				editorTabPane.getTabs().get(i).setClosable(true);
				simulationTabPane.getTabs().get(i).setClosable(true);
			}
		}
	}

	private void addWorkspace() {
		workspaces.add(new Workspace(this));
	}

	@Override
	public void didChangeLanguage(Workspace workspace,
			ResourceBundle newLanguage) {
		this.language = newLanguage;
		if (workspaceToTabs.containsKey(workspace)) {
			int workspaceNum = Integer.parseInt(workspaceToTabs
					.get(workspace)
					.getKey()
					.getText()
					.substring(
							workspaceToTabs.get(workspace).getKey().getText()
									.indexOf(" ") + 1));
			workspaceToTabs
					.get(workspace)
					.getKey()
					.setText(
							newLanguage.getString("Workspace") + " "
									+ workspaceNum);
			workspaceToTabs
					.get(workspace)
					.getValue()
					.setText(
							newLanguage.getString("Workspace") + " "
									+ workspaceNum);
		}
	}
}
