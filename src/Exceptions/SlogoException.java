package Exceptions;
/**
 * @author Tavo Loaiza
 *
 */
public class SlogoException extends Exception {
	/**
	 * Custom exception class for Slogo. Holds custom error message
	 */
	private static final long serialVersionUID = -6021616022834288844L;
	
	private String text;
	
	/**Creates a new Slogo Exception with the given error message
	 * @param errorMessage
	 */
	public SlogoException(String errorMessage){
		text = errorMessage;
	}
	
	/**Returns the text of the error message
	 * @return text of the error
	 */
	public String getText(){
		return text;
	}
	/**Sets the error text
	 * @param string to have as error text
	 */
	public void setText(String string) {
		text = string;	
	}
	
}