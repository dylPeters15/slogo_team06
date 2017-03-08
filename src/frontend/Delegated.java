/**
 * 
 */
package frontend;

/**
 * @author Dylan Peters
 *
 */
public interface Delegated<D extends EmptyDelegate> {

	void setDelegate(D delegate);
	
	D getDelegate();
	
	D createNonActiveDelegate();
	
}
