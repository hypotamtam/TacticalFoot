package org.tactical.sports.client.view.playground.layer.impl;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.UIConstants;
import org.tactical.sports.client.view.playground.MovementDirection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlayerView extends Composite implements MouseOutHandler, MouseOverHandler {

	private static PlayerUiBinder uiBinder = GWT.create(PlayerUiBinder.class);
	@UiField Image m_playerImage1;
	@UiField Image m_playerImage2;
	@UiField LayoutPanel m_layoutPanel;

	private int m_playerWidth = UIConstants.PLAYER_ANIMATION_WIDTH;
	private int m_playerHeight = UIConstants.PLAYER_ANIMATION_HEIGHT;

	private int m_frameCount = UIConstants.PLAYER_ANIMATION_FRAME_COUNT;
	private int m_highlightedFrameIndex = UIConstants.PLAYER_HIGHLIGHTED_FRAME_INDEX;
	private int m_idleFrameIndex = UIConstants.PLAYER_IDLE_FRAME_INDEX;
	
	private boolean m_isIdle;
	private boolean m_isPlayer1Displayed;

	private MovementDirection m_direction = MovementDirection.DOWN;

	interface PlayerUiBinder extends UiBinder<Widget, PlayerView> {
	}

	public PlayerView(boolean isLocal, boolean ishighlighable) {
		initWidget(uiBinder.createAndBindUi(this));
		m_layoutPanel.setPixelSize(m_playerWidth, m_playerHeight);

		initPlayerImage(m_playerImage1, isLocal);
		initPlayerImage(m_playerImage2, isLocal);
		
		m_isPlayer1Displayed = true;
		m_playerImage1.setVisible(m_isPlayer1Displayed);
		m_playerImage2.setVisible(!m_isPlayer1Displayed);

		if (ishighlighable) {
			m_layoutPanel.addDomHandler(this, MouseOverEvent.getType());
			m_layoutPanel.addDomHandler(this, MouseOutEvent.getType());
		}
		showIdleAnimation();
	}

	private void initPlayerImage(Image image, boolean isLocal) {
		m_layoutPanel.setWidgetTopHeight(image, 0, Unit.PX, m_playerHeight, Unit.PX);
		m_layoutPanel.setWidgetLeftWidth(image, 0, Unit.PX, m_playerWidth, Unit.PX);
		Res imageLoader = Res.INSTANCE;
		image.setResource(isLocal ? imageLoader.playerAnimations() :imageLoader.visitorPlayerAnimations());
		image.setVisibleRect(0, 0, m_playerWidth, m_playerHeight);
	}

	public int getWidth() {
		return m_playerWidth;
	}

	public int getHeight() {
		return m_playerHeight;
	}

	public void setDirection(MovementDirection direction) {
		if (direction == MovementDirection.NONE) {
			showIdleAnimation();
		} else {
			m_direction = direction;
			m_isIdle = false;
		}
	}

	public void showIdleAnimation() {
		m_isIdle = true;
		int left = m_idleFrameIndex * m_playerWidth;
		int top = getTopCorner();
		Image visibleImage = m_isPlayer1Displayed ? m_playerImage1 : m_playerImage2;
		visibleImage.setVisibleRect(left, top, m_playerWidth, m_playerHeight);
	}
	
	public void update(double progress) {
		if (!m_isIdle) {
			int frameIndex = (((int) (Math.round(progress * 2 * m_frameCount))) % m_frameCount);
			int left = frameIndex * m_playerWidth;
			int top = getTopCorner();
			updateAnimation(left, top);
		}
	}

	private void updateAnimation(int left, int top) {
		int lastLeft = m_isPlayer1Displayed ? m_playerImage1.getOriginLeft() : m_playerImage2.getOriginLeft();
		if (lastLeft != left) {
			m_isPlayer1Displayed = !m_isPlayer1Displayed;
			m_playerImage1.setVisible(m_isPlayer1Displayed);
			m_playerImage2.setVisible(!m_isPlayer1Displayed);
		} else {
			int nextLeft = left + m_playerWidth;
			if (nextLeft >= m_playerWidth * m_frameCount) {
				nextLeft = 0;
			}
			if (m_isPlayer1Displayed) {
				m_playerImage1.setVisibleRect(left, top, m_playerWidth, m_playerHeight);
				m_playerImage2.setVisibleRect(nextLeft, top, m_playerWidth, m_playerHeight);
			} else {
				m_playerImage2.setVisibleRect(left, top, m_playerWidth, m_playerHeight);
				m_playerImage1.setVisibleRect(nextLeft, top, m_playerWidth, m_playerHeight);
			}
		}
	}

	private int getTopCorner() {
		int top = 0;
		switch (m_direction) {
		case UP_LEFT:
			top = m_playerHeight;
			break;
		case DOWN_LEFT:
			top = 2 * m_playerHeight;
			break;
		case DOWN:
			top = 3 * m_playerHeight;
			break;
		case DOWN_RIGHT:
			top = 4 * m_playerHeight;
			break;
		case UP_RIGHT:
			top = 5 * m_playerHeight;
			break;
		default:
			break;
		}
		return top;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		int left = m_highlightedFrameIndex * m_playerWidth;
		int top = getTopCorner();
		Image visibleImage = m_isPlayer1Displayed ? m_playerImage1 : m_playerImage2;
		visibleImage.setVisibleRect(left, top, m_playerWidth, m_playerHeight);
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		showIdleAnimation();
	}
	
}
