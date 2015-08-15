package org.tactical.sports.server.rule.solver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.rule.RulesConstants;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.Movement;
import org.tactical.sports.shared.rule.solver.PlayersRoundStep;
import org.tactical.sports.shared.rule.solver.RoundResolution;
import org.tactical.sports.shared.rule.solver.RoundSolution;
import org.tactical.sports.shared.utils.Utils;

public class RoundSolver {

	private Collection<RoundActionSolver> m_roundActionSolver;

	private Random m_random;

	public RoundSolver(long randomSeed, RoundActionSolver... actionSolver) {
		m_roundActionSolver = Arrays.asList(actionSolver);
		m_random = new Random(randomSeed);
		for (RoundActionSolver roundActionSolver : actionSolver) {
			roundActionSolver.setRandom(m_random);
		}
	}

	public RoundSolution solveActions(Collection<Action> actions, Playground playground, Match match) {
		for (RoundActionSolver actionSolver : m_roundActionSolver) {
			actionSolver.setPlayground(playground);
			for (Action action : actions) {
				actionSolver.addAction(action);
			}
			actionSolver.presolvedRoundActions();
		}

		RoundSolution ret = new RoundSolution(match);
		RoundResolution roundResolution = new RoundResolution();
		ret.setResolution(roundResolution);

		boolean hasFinished = false;
		int stepIndex = 0;

		Tile tileWithBall = null;
		for (Tile tile : playground.getTiles()) {
			if (tile.hasBall()) {
				tileWithBall = tile;
				break;
			}
		}

		while (!hasFinished) {
			hasFinished = true;
			for (RoundActionSolver actionSolver : m_roundActionSolver) {
				actionSolver.solvedStep(stepIndex, roundResolution);
				hasFinished &= actionSolver.hasFinished();
			}

			Movement ballMovement = roundResolution.getBallindexForStep(stepIndex);
			if (ballMovement != null) {
				tileWithBall = playground.getTile(ballMovement.getEnd());
			}
			PlayerDetails newPlayerWithBall = updatePlayerWithBall(tileWithBall);
			if (newPlayerWithBall != null) {
				roundResolution.setPlayerWithBall(newPlayerWithBall.getId(), tileWithBall.getIndex(), stepIndex);
			}
			
			for (Tile tile : playground.getGoals()) {
				if (tile.hasBall()) {
					hasFinished = true;
					stepIndex++;
					boolean isLocalTeamGoal = !playground.isLocalTeamGoal(tile); 
					ret.setGoal(stepIndex, isLocalTeamGoal);
					PlayersRoundStep step = getStartPlayerStep(playground, !isLocalTeamGoal);
					roundResolution.addPlayersStep(step);
					roundResolution.addBallMouvement(stepIndex, new Movement(tile.getIndex(), Utils.getBallStartPos(!isLocalTeamGoal)));
				}
			}

			stepIndex++;
		}

		roundResolution.setRoundSetpCount(stepIndex);
		roundResolution.setRoundIndex(match.getRoundIndex());
		match.setLocalScore(ret.getLocaleTeamScore());
		match.setVisitorScore(ret.getVisitorTeamScore());
		return ret;
	}

	private PlayersRoundStep getStartPlayerStep(Playground playground, boolean isLocaleTeamEngaged) {
		PlayersRoundStep step = new PlayersRoundStep();
		for (Tile playgroundTile : playground.getTiles()) {
			if (playgroundTile.hasPlayer()) {
				for (PlayerDetails player : playgroundTile.getPlayers()) {
					Movement movement = new Movement(playgroundTile.getIndex(), Utils.getStartPos(player.getId(), isLocaleTeamEngaged));
					step.setMovementForPlayer(movement, player.getId());
				}
			}
		}
		
		step.setPlayerWithBall(Utils.getPlayerStartingWithBall(isLocaleTeamEngaged), Utils.getBallStartPos(isLocaleTeamEngaged));
		return step;
	}

	private PlayerDetails updatePlayerWithBall(Tile tileWithBall) {
		Collection<PlayerDetails> tilePlayers = tileWithBall.getPlayers();
		if (tilePlayers.isEmpty()) {
			return null;
		}

		PlayerDetails player = getPlayerHasBall(tilePlayers);
		if (player == null) {
			Object[] players = tilePlayers.toArray();
			player = (PlayerDetails) players[m_random.nextInt(players.length)];
			player.setHasBall(true);
		} else {
			for (PlayerDetails playerDetails : tilePlayers) {
				if (playerDetails.getTeamId() != player.getTeamId()) {
					if (m_random.nextDouble() < RulesConstants.LUCK_TO_GET_BALL) {
						playerDetails.setHasBall(true);
						player.setHasBall(false);
						player = playerDetails;
						break;
					}
				}
			}
		}
		return player;
	}

	private PlayerDetails getPlayerHasBall(Collection<PlayerDetails> players) {
		for (PlayerDetails playerDetails : players) {
			if (playerDetails.hasBall()) {
				return playerDetails;
			}
		}
		return null;
	}

}
