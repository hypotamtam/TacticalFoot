package org.tactical.sports.client.network.polling;


public abstract class AbstarctPollingResquest<T> implements PollingRequest<T> {

	private boolean m_isFinished;
	
	protected void finished() {
		m_isFinished = true;
	}
	
	@Override
	public boolean isFinished() {
		return m_isFinished;
	}

}
