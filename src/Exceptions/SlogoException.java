package Exceptions;
/**
 * @author Tavo Loaiza
 *
 */
public class SlogoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6021616022834288844L;
	
	private String text;
	
	public SlogoException(String errorMessage){
		text = errorMessage;
	}
	
	public String getText(){
		return text;
	}
	public void setText(String string) {
		text = string;	
	}
	
}