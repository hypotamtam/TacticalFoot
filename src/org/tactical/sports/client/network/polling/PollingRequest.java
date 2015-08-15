package org.tactical.sports.client.network.polling;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PollingRequest<T> extends AsyncCallback<T> {
	
	void doAnIteration();
	boolean isFinished();
	
}
