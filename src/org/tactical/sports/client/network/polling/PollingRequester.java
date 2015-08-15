package org.tactical.sports.client.network.polling;

import com.google.gwt.user.client.Timer;

public class PollingRequester<T> {
	
	private PollingRequest<T> m_pollingRequest;
	
	private Timer m_timer;

	private int m_periodMillis;

	public PollingRequester(PollingRequest<T> pollingRequest, int periodMillis) {
		m_pollingRequest = pollingRequest;
		m_periodMillis = periodMillis;
		m_timer = new Timer() {
			
			@Override
			public void run() {
				if (m_pollingRequest.isFinished()) {
					m_timer.cancel();
				} else {
					m_pollingRequest.doAnIteration();
				}
			}
		};
		
	}
	
	public void start() {
		m_timer.scheduleRepeating(m_periodMillis);		
	}
}
