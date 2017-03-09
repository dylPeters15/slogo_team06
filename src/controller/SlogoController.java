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
 * This class is the controller for the Slogo IDE. This is where much of the
 * Slogo-specific code is placed, in order to allow all of the other classes to
 * create a reusable API. The SlogoController sets up 1) the IDE that the user
 * interacts with in order to control the turtle, 2) the visualization of the
 * turtle, 3) the connections between the two (the model), and then displays the
 * IDE and the turtle visualization.
 * 
 * The SlogoController creates tabbed panes for both the IDE and the simulation
 * (turtle visualization), and it synchronizes these TabPanes so that when the
 * user changes tabs on one pane, the other one automatically switches as well.
 * It also synchronizes the language and stylesheets of each. This allows the
 * user to arrange the workspace any way they want to in order to best use
 * screen space, while preventing the code from becoming Spaghetti code; i.e.
 * the editor does not have a reference to the simulation because it does not
 * control the simulation (it controls the model), and vice versa.
 * 
 * The SlogoController implements WorkspaceDelegate in order to detect when a
 * workspace changes language. When it does change language, the controller
 * changes the title of that tab to be in that language.
 * 
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

	/**
	 * Creates a new instance of SlogoController. All values are set to
	 * defaults.
	 */
	public SlogoController() {
		this(new Stage());
	}

	/**
	 * Creates a new instance of SlogoController. The stage that is passed as a
	 * parameter is used as the stage for the editor, and the simulation is
	 * placed in a new stage. This method allows the Application class to pass
	 * its stage to the SlogoController.
	 * 
	 * @param stage
	 *            Stage used to display the editor of the IDE.
	 */
	public SlogoController(Stage stage) {
		language = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE);
		numWorkspacesThatHaveExisted = 0;
		workspaceToTabs = new HashMap<Workspace, Pair<Tab, Tab>>();
		initializeTabPanes();
		initializeStages(stage);
		initializeWorkspaces();
		editorStage.show();
		simulationStage.show();
	}

	/**
	 * Implementation of the didChangeLanguage method from the WorkspaceDelegate
	 * interface. Called by the Workspace when it changes languages.
	 * 
	 * When the language of a workspace is changed, the SlogoController changes
	 * the language of the title of the tab that holds the editor and the
	 * simulation.
	 * 
	 * @param workspace
	 *            The workspace that changed its language.
	 * @param newLanguage
	 *            The language that the workspace is now displayed in.
	 */
	@Override
	public void didChangeLanguage(Workspace workspace,
			ResourceBundle newLanguage) {
		if (workspaceToTabs.containsKey(workspace)) {
			int workspaceNum = Integer.parseInt(workspaceToTabs
					.get(workspace)
					.getKey()
					.getText()
					.substring(
							workspaceToTabs.get(workspace).getKey().getText()
									.lastIndexOf(" ") + 1));
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

	private void initializeTabPanes() {
		editorTabPane = new TabPane();
		simulationTabPane = new TabPane();
		Tab addTab = new Tab("+");
		addTab.setClosable(false);
		editorTabPane.getTabs().add(addTab);
		simulationTabPane.getTabs().add(addTab);

		editorTabPane.getSelectionModel().selectedItemProperty()
				.addListener(createTabPaneListener(addTab));
		simulationTabPane.getSelectionModel().selectedItemProperty()
				.addListener(createTabPaneListener(addTab));
	}

	private void initializeStages(Stage stage) {
		editorStage = stage;
		simulationStage = new Stage();

		editorStage.setOnCloseRequest(event -> requestAllClose());
		simulationStage.setOnCloseRequest(event -> requestAllClose());

		editorStage.setScene(new Scene(editorTabPane));
		simulationStage.setScene(new Scene(simulationTabPane));

		editorStage.setTitle(EDITOR_TITLE);
		simulationStage.setTitle(SIMULATOR_TITLE);
	}

	private void initializeWorkspaces() {
		workspaces = FXCollections.observableArrayList();
		workspaces.addListener(new ListChangeListener<Workspace>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Workspace> change) {
				workspacesModified(change);
			}
		});

		addWorkspace();
	}

	private void workspacesModified(
			ListChangeListener.Change<? extends Workspace> change) {
		if (change.next() && change.wasAdded()) {
			for (Workspace workspace : change.getAddedSubList()) {
				createTabsForWorkspace(workspace);
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
		selectLastTab();
	}

	private void createTabsForWorkspace(Workspace workspace) {
		workspaceToTabs.put(
				workspace,
				new Pair<Tab, Tab>(new Tab(language.getString("Workspace")
						+ " " + numWorkspacesThatHaveExisted, workspace
						.getEditorParent()), new Tab(language
						.getString("Workspace")
						+ " "
						+ numWorkspacesThatHaveExisted++, workspace
						.getSimulationParent())));
		workspaceToTabs.get(workspace).getKey()
				.setOnClosed(event -> workspaces.remove(workspace));
		editorTabPane.getTabs().add(editorTabPane.getTabs().size() - 1,
				workspaceToTabs.get(workspace).getKey());

		workspaceToTabs.get(workspace).getValue()
				.setOnClosed(event -> workspaces.remove(workspace));
		simulationTabPane.getTabs().add(simulationTabPane.getTabs().size() - 1,
				workspaceToTabs.get(workspace).getValue());
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

	private void selectLastTab() {
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

	private ChangeListener<Tab> createTabPaneListener(Tab addTab) {
		return new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable,
					Tab oldTab, Tab newTab) {
				if (!oldTab.equals(addTab) && newTab.equals(addTab)) {
					addWorkspace();
				} else if (!newTab.equals(addTab)) {
					TabPane pane1 = simulationTabPane;
					TabPane pane2 = editorTabPane;
					if (editorTabPane.getTabs().contains(newTab)) {
						pane1 = editorTabPane;
						pane2 = simulationTabPane;
					}
					pane2.getSelectionModel().select(
							pane1.getSelectionModel().getSelectedIndex());
				}
			}
		};
	}

	private void addWorkspace() {
		Workspace workspace = new Workspace();
		workspace.setDelegate(this);
		workspaces.add(workspace);
	}

	private void requestAllClose() {
		for (Workspace workspace : workspaces) {
			workspace.pepareToClose();
		}
		editorStage.close();
		simulationStage.close();
	}
}
