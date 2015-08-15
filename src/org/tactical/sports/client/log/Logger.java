package org.tactical.sports.client.log;

import org.tactical.sports.client.log.view.LogView;

public class Logger {

	static private Logger s_logger = new Logger(); 
	
	private LogView m_view;
	
	private Logger() {
	}
	
	static public void log(String text) {
		s_logger.add(text);
	}
	
	static public Logger getInstance() {
		return s_logger;
	}
	
	public void add(String log) {
		if (m_view != null) {
			m_view.log(log);
		}
		System.out.println(log);
	}
	
	public void setLogView(LogView logView) {
		m_view = logView;
	}
}
