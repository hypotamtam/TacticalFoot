package org.tactical.sports.client.view.gameevent;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.gameevent.GameEventView.GameEventPresenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;

/**
 * View base interface.
 * Extends IsWidget so a view impl can easily provide its container widget.
 */
public interface GameEventView extends ViewWithPresenter<GameEventPresenter> {
  
	void showEvent(String eventText);

	public interface GameEventPresenter extends Presenter<GameEventView>{
		void eventShown();
	}
}
