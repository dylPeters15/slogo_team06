/**
 * 
 */
package frontend.help;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import frontend.SlogoBaseUIManager;

/**
 * @author Dylan Peters
 *
 */
public class TabbedHTMLDisplayManager extends SlogoBaseUIManager<Region>{
	private static final boolean CLOSEABLE = false;

	TabPane tabPane;
	private List<String> urlList;
	
	
	public TabbedHTMLDisplayManager(List<String> urlList) {
		tabPane = new TabPane();
		this.urlList = urlList;
		populate();
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> observable,
					ResourceBundle oldValue, ResourceBundle newValue) {
				populate();
			}
		});
	}
	
	private void populate(){
		tabPane.getTabs().clear();
		int i = 1;
		for (String url : urlList){
			addTab(url,i++);
		}
	}
	
	private void addTab(String url, int index){
		Tab tab = new Tab();
		tab.setContent(new HTMLDisplayManager(url).getObject());
		tab.setClosable(CLOSEABLE);
		tab.setText(getLanguage().getValue().getString("Help")+" "+index);
		tabPane.getTabs().add(tab);
	}
	
	@Override
	public Region getObject() {
		return tabPane;
	}

}
