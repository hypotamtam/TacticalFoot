package org.tactical.sports.client.view;

import java.util.List;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.MatchChooserView.MatchChooserPresenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;

public interface MatchChooserView extends ViewWithPresenter<MatchChooserPresenter> {
	
	void setMatchList(List<String> matchlist);
	void setEmptyMessage(String message);

	public interface MatchChooserPresenter extends Presenter<MatchChooserView> {
		void onMatchChoosen(String match);
		void onMatchCreated();
	} 

}
