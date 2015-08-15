package org.tactical.sports.client.view.roundtime;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;
import org.tactical.sports.client.view.roundtime.RoundTime.RoundTimePresenter;

public interface RoundTime extends ViewWithPresenter<RoundTimePresenter> {
	void start(int duration);

	void stop();

	public interface RoundTimePresenter extends Presenter<RoundTime> {
		void onRoundStart();

		void onRoundTimeStop();
	}
}
