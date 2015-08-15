package org.tactical.sports.client.activity;

import org.tactical.sports.client.network.Future;
import org.tactical.sports.client.network.FutureListener;
import org.tactical.sports.client.view.WaitingView;

public class WaitingFuture<T> extends Future<T> {

	private WaitingView m_waitingView;

	public WaitingFuture(FutureListener<T> listener, String waitingMessage) {
		super(listener);
		m_waitingView = WaitingView.createWaitingView(waitingMessage);
		m_waitingView.show();
	}

	@Override
	public void onFailure(Throwable caught) {
		super.onFailure(caught);
		m_waitingView.hide();
	}

	@Override
	public void onSuccess(T result) {
		super.onSuccess(result);
		m_waitingView.hide();
	}


}
