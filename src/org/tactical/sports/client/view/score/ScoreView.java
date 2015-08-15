package org.tactical.sports.client.view.score;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;
import org.tactical.sports.client.view.score.ScoreView.ScorePresenter;

/**
 * View base interface.
 * Extends IsWidget so a view impl can easily provide its container widget.
 */
public interface ScoreView extends ViewWithPresenter<ScorePresenter> {
  
	public void setLocalTeamName(String name);
	public void setLocalTeamScore(int score);
	
	public void setVisitorTeamName(String name);
	public void setVisitorTeamScore(int score);
	
	public interface ScorePresenter extends Presenter<ScoreView>{
		
	}
}
