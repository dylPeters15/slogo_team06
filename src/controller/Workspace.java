/**
 * 
 */
package controller;

import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import backend.Model;
import frontend.SlogoDelegatedUIManager;
import frontend.editor.EditorPaneManager;
import frontend.editor.EditorPaneManagerDelegate;
import frontend.simulation.SimulationPaneManager;

/**
 * Workspace unifies the Model, EditorPaneManager, and SimulationPaneManager
 * into one Slogo class. Instantiating a Workspace instantiates an
 * EditorPaneManager object, a SimulationPaneManager object, and a Model object,
 * and connects them to each other via the patterns of delegation that they are
 * designed to use.
 * 
 * Workspace extends SlogoDelegatedUIManager because it implements all of the
 * behavior found in that class: it has a delegate (of type WorkspaceDelegate),
 * manages an object (the EditorPaneManager and SimulationManager), changes
 * languages, and uses stylesheets. The main difference between the Workspace
 * and SlogoDelegatedUIManager is that the Workspace manages two objects (the
 * editor and the simulation). Therefore, when implementing the abstract
 * getObject method from SlogoBaseUIManager, the Workspace simply creates a
 * SplitPane and adds both views to it. However, the Workspace also has
 * getEditor and getSimulation methods to allow other classes to access just
 * one.
 * 
 * Workspace implements the EditorPaneManagerDelegate interface in order to
 * allow the EditorPaneManager to notify the Workspace when it changes
 * languages. When the EditorPaneManager changes languages, the Workspace then
 * changes the language of the SimulationPaneManager as well.
 * 
 * @author Dylan Peters
 *
 */
public class Workspace extends
		SlogoDelegatedUIManager<WorkspaceDelegate, Parent> implements
		EditorPaneManagerDelegate {

	private EditorPaneManager editor;
	private SimulationPaneManager simulation;
	private SplitPane split;

	/**
	 * Creates a new instance of Workspace. Sets all values to default.
	 */
	public Workspace() {
		Model model = new Model();

		simulation = new SimulationPaneManager(model.getStatesList());
		new Scene(simulation.getObject());

		editor = new EditorPaneManager(model);
		editor.setDelegate(this);
		new Scene(editor.getObject());

		split = new SplitPane();
		split.getItems().add(editor.getObject());
		split.getItems().add(simulation.getObject());

	}

	/**
	 * Returns a Parent containing both the EditorPaneManager's Parent and the
	 * SimulationPaneManager's Parent
	 * 
	 * @return a Parent containing both the EditorPaneManager's Parent and the
	 *         SimulationPaneManager's Parent
	 */
	@Override
	public Parent getObject() {
		return split;
	}

	/**
	 * Returns the Parent from the SimulationPaneManager that is created within
	 * the workspace.
	 * 
	 * @return the Parent from the SimulationPaneManager that is created within
	 *         the workspace.
	 */
	public Parent getEditorParent() {
		return editor.getObject();
	}

	/**
	 * Returns the Parent from the EditorPaneManager that is created within the
	 * workspace.
	 * 
	 * @return the Parent from the EditorPaneManager that is created within the
	 *         workspace.
	 */
	public Parent getSimulationParent() {
		return simulation.getObject();
	}

	/**
	 * This is the concrete implementation of the method in WorkspaceDelegate.
	 * This method is called by the EditorPaneManager when the user changes the
	 * language.
	 * 
	 * This method changes the language ResourceBundle for both the editor and
	 * simulation.
	 */
	@Override
	public void userDidRequestChangeToLanguage(ResourceBundle newLanguage) {
		getDelegate().didChangeLanguage(this, newLanguage);
	}

	/**
	 * This is the concrete implementation of the method in WorkspaceDelegate.
	 * This method is called by the EditorPaneManager when the user changes the
	 * style.
	 * 
	 * This method changes the stylesheet for both the editor and simulation.
	 */
	@Override
	public void userDidRequestChangeToStylesheet(String stylesheet) {
		simulation.setStyleSheet(stylesheet);
		editor.setStyleSheet(stylesheet);
	}

	/**
	 * This is the concrete implementation of the createNonActiveDelegate method
	 * declared in the Delegated interface.
	 * 
	 * This implementation creates an instance of WorkspaceDelegate in which all
	 * of the methods do nothing, as is specified by the Delegated interface.
	 */
	@Override
	public WorkspaceDelegate createNonActiveDelegate() {
		return new WorkspaceDelegate() {
			@Override
			public void didChangeLanguage(Workspace workspace,
					ResourceBundle newLanguage) {
			}
		};
	}

	/**
	 * This method should be called when the Workspace is being closed. It
	 * ensures that all windows created by the workspace are closed when the
	 * workspace closes.
	 */
	public void pepareToClose() {
		editor.closeAllChildWindows();
	}

	@Override
	protected void languageResourceBundleDidChange() {
		editor.setLanguageResourceBundle(getLanguageResourceBundle());
		simulation.setLanguageResourceBundle(getLanguageResourceBundle());
		userDidRequestChangeToLanguage(getLanguageResourceBundle());
	}

	@Override
	protected void styleSheetDidChange() {
		userDidRequestChangeToStylesheet(getStyleSheet());
	}

}