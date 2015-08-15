package org.tactical.sports.client.view;

import org.tactical.sports.client.view.GameView.GameViewPresenter;
import org.tactical.sports.client.view.action.GameActionMenu;
import org.tactical.sports.client.view.gameevent.GameEventView;
import org.tactical.sports.client.view.gameevent.GameEventViewImpl;
import org.tactical.sports.client.view.helper.CompositeWithPresenter;
import org.tactical.sports.client.view.playground.PlaygroundView;
import org.tactical.sports.client.view.playground.PlaygroundViewImpl;
import org.tactical.sports.client.view.roundtime.RoundTime;
import org.tactical.sports.client.view.roundtime.RoundTimeImpl;
import org.tactical.sports.client.view.score.ScoreView;
import org.tactical.sports.client.view.score.ScoreViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import org.tactical.sports.client.view.action.GameActionImpl;

public class GameViewImpl extends CompositeWithPresenter<GameViewPresenter> implements GameView {

	private static GameViewBisUiBinder uiBinder = GWT.create(GameViewBisUiBinder.class);
	@UiField RoundTimeImpl m_roundTimePanel;
	@UiField PlaygroundViewImpl m_playgroundView;
	@UiField LayoutPanel m_gamePanel;
	@UiField GameEventViewImpl m_eventView;
	@UiField ScoreViewImpl m_scoreView;
	@UiField GameActionImpl m_gameActionMenu;

	interface GameViewBisUiBinder extends UiBinder<Widget, GameViewImpl> {
	}

	public GameViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		m_eventView.getElement().getStyle().setPosition(Position.ABSOLUTE);
		m_playgroundView.getElement().getStyle().setPosition(Position.ABSOLUTE);
		m_gameActionMenu.getElement().getStyle().setPosition(Position.ABSOLUTE);
		m_playgroundView.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
	}

	@Override
	public PlaygroundView getPlaygroundView() {
		return m_playgroundView;
	}

	@Override
	public GameActionMenu getActionMenu() {
		return m_gameActionMenu;
	}

	@Override
	public RoundTime getRoundTime() {
		return m_roundTimePanel;
	}
	
	@Override
	public GameEventView getGameEventView() {
		return m_eventView;
	}

	@Override
	public ScoreView getScoreView() {
		return m_scoreView;
	}

	@Override
	public void updateLayout() {
		setPixelSize(m_playgroundView.getOffsetWidth() + m_roundTimePanel.getOffsetWidth(),//+ UIConstants.ROUND_TIME_VIEW_WIDTH,
					 m_playgroundView.getOffsetHeight() + m_scoreView.getOffsetHeight()/*+ UIConstants.SCORE_VIEW_HEIGHT*/);
	}
	
	@UiHandler("m_gamePanel")
	void onGamePanelAttachOrDetach(AttachEvent event) {
		updateLayout();
	}

}
