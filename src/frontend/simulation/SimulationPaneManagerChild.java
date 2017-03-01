package frontend.simulation;

abstract class SimulationPaneManagerChild<D extends SimulationPaneManagerChildDelegate> {
	
	private D delegate;
	
	SimulationPaneManagerChild(D delegate){
		setDelegate(delegate);
	}
	
	D getDelegate() {
		return delegate;
	}

	void setDelegate(D delegate) {
		this.delegate = delegate;
	}

}
