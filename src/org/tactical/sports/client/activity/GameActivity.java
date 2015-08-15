package org.tactical.sports.client.activity;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.ClientFactory;
import org.tactical.sports.client.activity.place.GamePlace;
import org.tactical.sports.client.activity.place.HomePlace;
import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.activity.presenter.game.GameActionMenuPresenterImpl;
import org.tactical.sports.client.activity.presenter.game.GameEventPresenterImpl;
import org.tactical.sports.client.activity.presenter.game.PlaygroundPresenterImpl;
import org.tactical.sports.client.activity.presenter.game.RoundTimePresenterImpl;
import org.tactical.sports.client.event.action.ActionEvent;
import org.tactical.sports.client.event.action.ActionEventHandler;
import org.tactical.sports.client.event.playground.PlaygroundChangedEvent;
import org.tactical.sports.client.event.round.RoundBeginEvent;
import org.tactical.sports.client.event.round.RoundBeginEventHandler;
import org.tactical.sports.client.event.round.RoundEndedEvent;
import org.tactical.sports.client.event.round.RoundEndedEventHandler;
import org.tactical.sports.client.log.Logger;
import org.tactical.sports.client.network.Future;
import org.tactical.sports.client.network.FutureListener;
import org.tactical.sports.client.network.MatchStarterService;
import org.tactical.sports.client.network.RoundSolverService;
import org.tactical.sports.client.view.GameView;
import org.tactical.sports.client.view.GameView.GameViewPresenter;
import org.tactical.sports.client.view.action.GameActionMenu;
import org.tactical.sports.client.view.gameevent.GameEventView;
import org.tactical.sports.client.view.gameevent.GameEventView.GameEventPresenter;
import org.tactical.sports.client.view.playground.PlaygroundView;
import org.tactical.sports.client.view.roundtime.RoundTime;
import org.tactical.sports.client.view.roundtime.RoundTime.RoundTimePresenter;
import org.tactical.sports.client.view.score.ScoreView;
import org.tactical.sports.shared.Constants;
import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.manager.MovementActionManager;
import org.tactical.sports.shared.rule.action.manager.PassActionManager;
import org.tactical.sports.shared.rule.solver.RoundSolution;
import org.tactical.sports.shared.utils.Utils;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GameActivity extends AbstractPresenter<GameView> implements Activity, GameViewPresenter, ActionEventHandler, RoundBeginEventHandler, RoundEndedEventHandler, FutureListener<RoundSolution> {

	private ClientFactory m_clientFactory;

	private GameActionMenuPresenterImpl m_menuPresenter;
	private PlaygroundPresenterImpl m_playgroundPresenter;
	private RoundTimePresenter m_roundTimePresenter;

	private Match m_match;
	private long m_matchId;
	private long m_userTeamId;
	
	private RoundSolution m_lastSolution;
	private Map<PlayerDetails, Action> m_actionByPlayer;
	

	public GameActivity(GamePlace place, ClientFactory clientFactory) {
		m_clientFactory = clientFactory;
		m_actionByPlayer = new HashMap<PlayerDetails, Action>();
		setEventBus(m_clientFactory.getEventBus());
		setDisplay(m_clientFactory.getGameView());
		
		m_userTeamId = place.getUserTeamId();
		m_matchId = place.getMatchId();
		m_match = place.getMatch();
	}

	private void startMatch() {
		Logger.log("Play match " + m_match);
		initFootPlayground();
		initScore();
		
		getDisplay().updateLayout();
		PlaygroundChangedEvent event = new PlaygroundChangedEvent(m_playgroundPresenter.getPlayground());
		m_menuPresenter.onPlaygroundChanged(event);
		/*
		 * Patch moche mais la ligne suivante ne marche pas.
		 * TODO: investiguer le pourquoi du comment.
		 * getEventBus().fireEvent(event);
		 */
		
		m_roundTimePresenter.onRoundStart();
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		initActionMenu();
		initRoundTime();
		initGameEvent();

		containerWidget.setWidget(getDisplay());
		
		if (m_match == null) {
			new MatchStarterService().getMatch(m_matchId, new WaitingFuture<Match>(new FutureListener<Match>() {

				@Override
				public void onDone(Future<Match> future) {
					//TODO: Pour faire ça il faut savoir creer le playground en cour de match.
//					try {
//						m_match = future.getResult();
//						startMatch();
//					} catch (Throwable e) {
						HomePlace homePlace = new HomePlace();
						m_clientFactory.getPlaceController().goTo(homePlace);
//					}
				}
			}, "Retrieving the match"));
		} else {
			startMatch();
		}
	}

	private void initGameEvent() {
		GameEventPresenter gameEventPresenter = new GameEventPresenterImpl();
		GameEventView gameEventView = getDisplay().getGameEventView();
		gameEventPresenter.setDisplay(gameEventView);
		gameEventView.setPresenter(gameEventPresenter);
		gameEventPresenter.setEventBus(getEventBus());
	}

	private void initScore() {
		ScoreView scoreView = getDisplay().getScoreView();
		scoreView.setLocalTeamName(m_match.getLocal().getName());
		scoreView.setLocalTeamScore(m_match.getLocalScore());
		scoreView.setVisitorTeamName(m_match.getVisitor().getName());
		scoreView.setVisitorTeamScore(m_match.getVisitorScore());
	}

	private void initRoundTime() {
		RoundTime roundTime = getDisplay().getRoundTime();
		m_roundTimePresenter = new RoundTimePresenterImpl();
		m_roundTimePresenter.setDisplay(roundTime);
		roundTime.setPresenter(m_roundTimePresenter);
		m_roundTimePresenter.setEventBus(m_clientFactory.getEventBus());
	}

	private void initActionMenu() {
		GameActionMenu menu = getDisplay().getActionMenu();
		m_menuPresenter = new GameActionMenuPresenterImpl();
		m_menuPresenter.setEventBus(m_clientFactory.getEventBus());
		m_menuPresenter.setDisplay(menu);
		menu.setPresenter(m_menuPresenter);
		m_menuPresenter.addActionManager(new MovementActionManager());
		m_menuPresenter.addActionManager(new PassActionManager());
	}

	private void initFootPlayground() {
		PlaygroundView view = getDisplay().getPlaygroundView();
	//	view.clear();
		m_playgroundPresenter = new PlaygroundPresenterImpl(Utils.createPlayground(m_match, true), m_userTeamId);
		m_playgroundPresenter.setEventBus(m_clientFactory.getEventBus());
		m_playgroundPresenter.setDisplay(view);
		view.setPresenter(m_playgroundPresenter);
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

	@Override
	public void onRoundStart() {
		m_match.increaseRoundIndex();
		m_actionByPlayer.clear();
		if (m_lastSolution.getResolution().hasGoal()) {
			ScoreView scoreView = getDisplay().getScoreView();
			scoreView.setLocalTeamScore(m_lastSolution.getLocaleTeamScore());
			scoreView.setVisitorTeamScore(m_lastSolution.getVisitorTeamScore());
		}
		if (m_match.getRoundIndex() == Constants.MATCH_ROUND_COUNT) {
			HomePlace homePlace = new HomePlace();
			m_clientFactory.getPlaceController().goTo(homePlace);
		}
		m_lastSolution = null;
	}

	@Override
	public void onRoundEnded() {
		m_menuPresenter.stop();
		RoundSolverService solver = new RoundSolverService(m_match, m_playgroundPresenter.getPlayground());
		solver.solvedActions(m_actionByPlayer.values(), m_userTeamId, new WaitingFuture<RoundSolution>(this, "Waiting for your opponent action"));
	}

	@Override
	public void onDone(Future<RoundSolution> future) {
		try {
			RoundSolution result = future.getResult();
			m_playgroundPresenter.showRoundSolution(result.getResolution());
			m_lastSolution = result;
		} catch (Throwable e) {
			Logger.log("Error waiting the round solution for round " + m_match.getRoundIndex() + " of match " + m_match.getId() + " " + e.getMessage());
		}
	}

	@Override
	public void onActionSet(ActionEvent actionEvent) {
		m_actionByPlayer.put(actionEvent.getAction().getPlayer(), actionEvent.getAction());
	}

	@Override
	public void onActionCancel(ActionEvent actionEvent) {
		m_actionByPlayer.remove(actionEvent.getAction().getPlayer());
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addHandler(ActionEvent.TYPE, this);
		eventBus.addHandler(RoundEndedEvent.TYPE, this);
		eventBus.addHandler(RoundBeginEvent.TYPE, this);
	}

}
