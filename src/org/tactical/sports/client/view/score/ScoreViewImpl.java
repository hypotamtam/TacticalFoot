package org.tactical.sports.client.view.score;

import org.tactical.sports.client.view.helper.CompositeWithPresenter;
import org.tactical.sports.client.view.score.ScoreView.ScorePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link ScoreView}.
 */
public class ScoreViewImpl extends CompositeWithPresenter<ScorePresenter> implements ScoreView {

	interface Binder extends UiBinder<Widget, ScoreViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	@UiField Label m_scoreLabel;

	private String m_localTeamName;
	private int m_localTeamScore;
	private String m_visitorTeamName;
	private int m_visitorTeamScore;
	
	public ScoreViewImpl() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setLocalTeamName(String name) {
		m_localTeamName = name;
		updateDisplay();
	}

	@Override
	public void setLocalTeamScore(int score) {
		m_localTeamScore = score;
		updateDisplay();
	}

	@Override
	public void setVisitorTeamName(String name) {
		m_visitorTeamName = name;
		updateDisplay();
	}

	@Override
	public void setVisitorTeamScore(int score) {
		m_visitorTeamScore = score;
		updateDisplay();
	}

	private void updateDisplay() {
		m_scoreLabel.setText(m_localTeamName + " " + m_localTeamScore + "-" + m_visitorTeamScore + " " + m_visitorTeamName);
	}
	
}
