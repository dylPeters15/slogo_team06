/**
 * 
 */
package frontend;

/**
 * @author Dylan Peters
 *
 */
public interface Delegated<D> {

	void setDelegate(D delegate);

	D getDelegate();

	D createNonActiveDelegate();

}
