/**
 * 
 */
package controller;

import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import backend.Model;
import frontend.editor.EditorPaneManager;
import frontend.editor.EditorPaneManagerDelegate;
import frontend.simulation.SimulationPaneManager;

/**
 * @author Dylan Peters
 *
 */
public class Workspace implements EditorPaneManagerDelegate {
	
	private EditorPaneManager editor;
	private SimulationPaneManager simulation;
	private WorkspaceDelegate delegate;

	public Workspace(ResourceBundle language, WorkspaceDelegate delegate) {
		Model model = new Model();

		simulation = new SimulationPaneManager(model.getStatesList());
		new Scene(simulation.getObject());

		editor = new EditorPaneManager(model);
		editor.setDelegate(this);
		new Scene(editor.getObject());

		setDelegate(delegate);
		setLanguage(language);

	}

	public void setDelegate(WorkspaceDelegate delegate) {
		this.delegate = delegate;
	}

	public WorkspaceDelegate getDelegate() {
		return delegate;
	}

	private void setLanguage(ResourceBundle language) {
		editor.setLanguageResourceBundle(language);
		simulation.setLanguageResourceBundle(language);
		didChangeToLanguage(language);
	}

	public Parent getEditorRegion() {
		return editor.getObject();
	}

	public Parent getSimulationRegion() {
		return simulation.getObject();
	}

	@Override
	public void didChangeToLanguage(ResourceBundle newLanguage) {
		if (delegate != null) {
			delegate.didChangeLanguage(this, newLanguage);
		}
	}

	@Override
	public void didChangeToStylesheet(String stylesheet) {
		simulation.setStyleSheet(stylesheet);
		editor.setStyleSheet(stylesheet);
	}

	public void close() {
		editor.close();
	}

}