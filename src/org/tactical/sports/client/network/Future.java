package org.tactical.sports.client.network;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class Future<T> implements AsyncCallback<T> {

	private T m_result = null;

	private Throwable m_error = null;

	private FutureListener<T> m_listener;

	public Future(FutureListener<T> listener) {
		m_listener = listener;
	}

	@Override
	public void onFailure(Throwable caught) {
		m_error = caught;
		m_listener.onDone(this);
	}

	@Override
	public void onSuccess(T result) {
		m_result = result;
		m_listener.onDone(this);
	}

	public T getResult() throws Throwable {
		if (m_error != null) {
			throw m_error;
		} 
		return m_result;
	}

}
