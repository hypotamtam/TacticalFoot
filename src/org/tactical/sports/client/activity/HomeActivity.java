package org.tactical.sports.client.activity;

import java.util.ArrayList;
import java.util.List;

import org.tactical.sports.client.ClientFactory;
import org.tactical.sports.client.activity.place.GamePlace;
import org.tactical.sports.client.activity.place.HomePlace;
import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.network.Future;
import org.tactical.sports.client.network.FutureListener;
import org.tactical.sports.client.network.MatchStarterService;
import org.tactical.sports.client.view.MatchChooserView;
import org.tactical.sports.client.view.MatchChooserView.MatchChooserPresenter;
import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.User;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HomeActivity extends AbstractPresenter<MatchChooserView> implements Activity, MatchChooserPresenter, FutureListener<Match>{

	private MatchStarterService m_matchStarterService = new MatchStarterService();

	private User m_user;
	private List<Match> m_matchList;

	private ClientFactory m_clientFactory;

	public HomeActivity(HomePlace place, ClientFactory clientFactory) {
		m_clientFactory = clientFactory;
		m_user = place.getUser();

		MatchChooserView matchChooserView = clientFactory.getMatchChooserView();
		matchChooserView.setPresenter(this);
		this.setDisplay(matchChooserView);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(getDisplay());
		listMatchNotStarted();
	}

	private void listMatchNotStarted() {
		m_matchStarterService.getMatchsNotStarted(new WaitingFuture<List<Match>>(new FutureListener<List<Match>>() {

			@Override
			public void onDone(Future<List<Match>> future) {
				try {
					m_matchList = future.getResult();
					if (m_matchList.isEmpty()) {
						getDisplay().setEmptyMessage("No match, sorry! Create a new one.");
					} else {
						List<String> matchList = new ArrayList<String>();
						for (Match match : m_matchList) {
							matchList.add(match.getLocal().getName());
						}
						getDisplay().setMatchList(matchList);
					}
				} catch (Throwable e) {
					getDisplay().setEmptyMessage("No match, sorry! Create a new one.");
				}
			}

		}, "Listing the opponents..."));
	}

	@Override
	public void onMatchChoosen(String matchName) {
		for (Match match : m_matchList) {
			if (match.getLocal().getName().equals(matchName)) {
				m_matchStarterService.joinMatch(m_user.getTeam(), match, new WaitingFuture<Match>(this,"In road to the stadium..."));
			}
		}
	}

	@Override
	public void onMatchCreated() {
		m_matchStarterService.createMachtAndWaitOpponent(m_user.getTeam(), new WaitingFuture<Match>(this,"Waiting for an opponent..."));
		listMatchNotStarted();
	}

	@Override
	public void onDone(Future<Match> future) {
		Match match;
		try {
			match = future.getResult();
			GamePlace nextPlace = new GamePlace(m_user.getTeam().getId());
	        nextPlace.setMatch(match);
	        m_clientFactory.getPlaceController().goTo(nextPlace);
		} catch (Throwable e) {
			
		} 
	}

	
	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void onStop() {
	}

}
