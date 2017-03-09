/**
 * 
 */
package frontend;

import javafx.scene.Parent;

/**
 * @author Dylan Peters
 *
 */
public abstract class SlogoDelegatedUIManager<D, T extends Parent> extends
		SlogoBaseUIManager<T> implements Delegated<D> {

	private D delegate;

	public SlogoDelegatedUIManager() {
		delegate = createNonActiveDelegate();
	}

	@Override
	public final void setDelegate(D delegate) {
		if (delegate == null) {
			delegate = createNonActiveDelegate();
		}
		this.delegate = delegate;
		delegateDidChange();
	}

	@Override
	public final D getDelegate() {
		return delegate;
	}

	protected void delegateDidChange() {

	}

}
