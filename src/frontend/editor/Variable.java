/**
 * 
 */
package frontend.editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dylan Peters
 *
 */
public class Variable implements Comparable<Variable> {

	private StringProperty nameProperty;
	private StringProperty valueProperty;
	private boolean isNumber;

	Variable(String name, String value, boolean isNumber) {
		nameProperty = new SimpleStringProperty(name);
		valueProperty = new SimpleStringProperty(value);
		this.isNumber = isNumber;
	}

	public StringProperty nameProperty() {
		return nameProperty;
	}

	public StringProperty valueProperty() {
		return valueProperty;
	}

	public boolean isNumber() {
		return isNumber;
	}

	@Override
	public int compareTo(Variable other) {
		return this.nameProperty().get().compareTo(other.nameProperty().get());
	}

	public boolean equals(Variable other) {
		return this.nameProperty().get().equals(other.nameProperty().get());
	}

}
