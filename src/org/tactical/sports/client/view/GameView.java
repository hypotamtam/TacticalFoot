package org.tactical.sports.client.view;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.GameView.GameViewPresenter;
import org.tactical.sports.client.view.action.GameActionMenu;
import org.tactical.sports.client.view.gameevent.GameEventView;
import org.tactical.sports.client.view.helper.ViewWithPresenter;
import org.tactical.sports.client.view.playground.PlaygroundView;
import org.tactical.sports.client.view.roundtime.RoundTime;
import org.tactical.sports.client.view.score.ScoreView;

public interface GameView extends ViewWithPresenter<GameViewPresenter> {

	PlaygroundView getPlaygroundView();
	
	GameActionMenu getActionMenu();
	
	RoundTime getRoundTime();
	
	GameEventView getGameEventView();
	
	ScoreView getScoreView();
	
	void updateLayout();
	
	public interface GameViewPresenter extends Presenter<GameView> {

	}

}
