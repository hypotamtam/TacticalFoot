package org.tactical.sports.client.view.animatedwidget.animation;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class AnimatedSprite extends AbstractAnimation implements IsWidget {

	private Image m_widget;
	
	private int m_spriteWidth;
	private int m_spriteCount;
	
	public AnimatedSprite(ImageResource sprites, int spriteCount) {
		m_spriteCount = spriteCount;
		m_spriteWidth = sprites.getWidth() / m_spriteCount;
		
		m_widget = new Image(sprites);
		m_widget.setVisibleRect(0, 0, m_spriteWidth, m_widget.getHeight());
	}
		
	@Override
	public void onUpdate(double progress) {
		super.onUpdate(progress);
		int spriteIndex = (int) (m_spriteCount * progress);
		int spriteLeft = spriteIndex * m_spriteWidth;
		m_widget.setVisibleRect(spriteLeft, 0, m_spriteWidth, m_widget.getHeight());
	}
	
	@Override
	public void onStart() {
	}

	
	public int getHeight() {
		return m_widget.getHeight();
	}
	
	public int getWidth() {
		return m_spriteWidth;
	}

	@Override
	public Widget asWidget() {
		return m_widget;
	}
}
