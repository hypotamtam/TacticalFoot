package org.tactical.sports.client.activity.presenter.game;


import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.event.round.RoundBeginEvent;
import org.tactical.sports.client.event.round.RoundBeginEventHandler;
import org.tactical.sports.client.event.round.RoundEndedEvent;
import org.tactical.sports.client.view.roundtime.RoundTime;
import org.tactical.sports.client.view.roundtime.RoundTime.RoundTimePresenter;
import org.tactical.sports.shared.Constants;

import com.google.gwt.event.shared.EventBus;


public class RoundTimePresenterImpl extends AbstractPresenter<RoundTime> implements RoundTimePresenter, RoundBeginEventHandler {


	
	public RoundTimePresenterImpl() {
	}

	@Override
	public void onRoundStart() {
		getDisplay().start(Constants.ROUND_TIME);
	}

	@Override
	public void onRoundTimeStop() {
		getDisplay().stop();
		RoundEndedEvent event = new RoundEndedEvent();
		getEventBus().fireEvent(event);
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addHandler(RoundBeginEvent.TYPE, this);
	}


}
