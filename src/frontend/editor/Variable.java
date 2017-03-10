/**
 * 
 */
package frontend.editor;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Variable class represents an object with a name and a value. The class
 * can be used by the front end or back end to represent Variables that the user
 * can manipulate.
 * 
 * The name field is a ReadOnlyStringProperty because every variable name is by
 * definition a string, and it cannot be changed. (Of course, new variables can
 * be added with the same value, but the name of the variable itself does not
 * change.) The value field is represented as a StringProperties because every
 * Variable in the Slogo language can be represented as a string. The variable
 * itself may be an integer, a double, a string, or a command, but all of these
 * can be represent as strings and interpreted as the original structure.
 * 
 * The value is represented as a Property in order to allow other classes to
 * modify, bind, or listen for changes to the variable.
 * 
 * The specific implementation of how the number values are translated to
 * Strings is left up to client code to increase flexibility.
 * 
 * @author Dylan Peters
 *
 */
public class Variable implements Comparable<Variable> {

	private ReadOnlyStringProperty nameProperty;
	private StringProperty valueProperty;
	private boolean isNumber;

	/**
	 * Creates a new instance of Variable. Sets the name and value fields to the
	 * parameters specified.
	 * 
	 * @param name
	 *            a String representing the name of the variable.
	 * @param value
	 *            a String representing the value of the variable.
	 * @param isNumber
	 *            a boolean that specifies whether the value of the variable is
	 *            a number or a String. This can be useful for client code, so
	 *            that the client code knows to interpret the value as a number.
	 */
	Variable(String name, String value, boolean isNumber) {
		nameProperty = new SimpleStringProperty(name);
		valueProperty = new SimpleStringProperty(value);
		this.isNumber = isNumber;
	}

	public ReadOnlyStringProperty nameProperty() {
		return nameProperty;
	}

	public StringProperty valueProperty() {
		return valueProperty;
	}

	boolean isNumber() {
		return isNumber;
	}

	/**
	 * Compares this Variable to the one passed as a parameter. By default,
	 * variables are compared alphabetically based on their name.
	 */
	@Override
	public int compareTo(Variable other) {
		return this.nameProperty().get().compareTo(other.nameProperty().get());
	}

	boolean equals(Variable other) {
		return this.nameProperty().get().equals(other.nameProperty().get());
	}

}
