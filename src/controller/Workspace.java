/**
 * 
 */
package controller;

import java.util.ResourceBundle;

import javafx.scene.Parent;
import backend.Model;
import frontend.editor.EditorPaneManager;
import frontend.editor.EditorPaneManagerDelegate;
import frontend.simulation.SimulationPaneManager;

/**
 * @author Dylan Peters
 *
 */
public class Workspace implements EditorPaneManagerDelegate {
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages/";
	private EditorPaneManager editor;
	private SimulationPaneManager simulation;
	private WorkspaceDelegate delegate;

	public Workspace() {
		this(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE), null);
	}

	public Workspace(ResourceBundle language) {
		this(language, null);
	}

	public Workspace(WorkspaceDelegate delegate) {
		this(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ DEFAULT_LANGUAGE), delegate);
	}

	public Workspace(ResourceBundle language, WorkspaceDelegate delegate) {
		Model model = new Model();
		simulation = new SimulationPaneManager(model.getStatesList());
		editor = new EditorPaneManager(model);
		editor.setDelegate(this);
		editor.setLanguageResourceBundle(language);
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