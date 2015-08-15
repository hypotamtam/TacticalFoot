package org.tactical.sports.client.ressource;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeUri;

public class FakeImageRessource implements ImageResource {

	String m_name;
	String m_url;
	int m_top;
	int m_left;
	int m_width;
	int m_height;
	boolean m_isAnimated;
	
	
	public String getName() {
		return m_name;
	}
	
	public void setName(String name) {
		m_name = name;
	}
	
	public String getURL() {
		return m_url;
	}
	
	public void setURL(String url) {
		m_url = url;
	}
	
	public int getTop() {
		return m_top;
	}
	
	public void setTop(int top) {
		m_top = top;
	}
	
	public int getLeft() {
		return m_left;
	}
	
	public void setLeft(int left) {
		m_left = left;
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public void setWidth(int width) {
		m_width = width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public void setHeight(int height) {
		m_height = height;
	}
	public boolean isAnimated() {
		return m_isAnimated;
	}
	public void setAnimated(boolean isAnimated) {
		m_isAnimated = isAnimated;
	}

	@Override
	public SafeUri getSafeUri() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
