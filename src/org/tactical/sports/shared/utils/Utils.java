package org.tactical.sports.shared.utils;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.FootPlayground;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public class Utils {

	private static final Map<Long, TileIndex> LOCAL_TEAM_POSITION_WITHOU_BALL = new HashMap<Long, TileIndex>() {
		private static final long serialVersionUID = 1L;
		{
			put(1L, new TileIndex(7, 3));
			put(2L, new TileIndex(7, 5));
			put(3L, new TileIndex(3, 2));
			put(4L, new TileIndex(3, 6));
		}
	};

	private static final Map<Long, TileIndex> LOCAL_TEAM_POSITION_WITH_BALL = new HashMap<Long, TileIndex>() {
		private static final long serialVersionUID = 1L;
		{
			put(1L, new TileIndex(8, 3));
			put(2L, new TileIndex(8, 4));
			put(3L, new TileIndex(3, 2));
			put(4L, new TileIndex(3, 6));
		}
	};

	private static final Map<Long, TileIndex> VISITOR_TEAM_POSITION_WITHOU_BALL = new HashMap<Long, TileIndex>() {
		private static final long serialVersionUID = 1L;
		{
			put(5L, new TileIndex(9, 3));
			put(6L, new TileIndex(9, 6));
			put(7L, new TileIndex(13, 2));
			put(8L, new TileIndex(13, 6));
		}
	};

	private static final Map<Long, TileIndex> VISITOR_TEAM_POSITION_WITH_BALL = new HashMap<Long, TileIndex>() {
		private static final long serialVersionUID = 1L;
		{
			put(5L, new TileIndex(8, 3));
			put(6L, new TileIndex(8, 4));
			put(7L, new TileIndex(13, 2));
			put(8L, new TileIndex(13, 6));
		}
	};

	public static Playground createPlayground(Match match, boolean isLocalTeamEngage) {
		Playground playground = new FootPlayground(17, 9);
		playground.setMatchId(match.getId());

		Map<Long, TileIndex> localPos = isLocalTeamEngage ? LOCAL_TEAM_POSITION_WITH_BALL : LOCAL_TEAM_POSITION_WITHOU_BALL;
		Long localeTeamId =  match.getLocal().getId();
		playground.addPlayer(createPlayer(1L, localeTeamId), localPos.get(1L), true);
		PlayerDetails player = createPlayer(2L, localeTeamId);
		player.setHasBall(isLocalTeamEngage);
		playground.addPlayer(player, localPos.get(2L), true);
		playground.addPlayer(createPlayer(3L, localeTeamId), localPos.get(3L), true);
		playground.addPlayer(createPlayer(4L, localeTeamId), localPos.get(4L), true);

		Map<Long, TileIndex> visitorPos = isLocalTeamEngage ? VISITOR_TEAM_POSITION_WITHOU_BALL : VISITOR_TEAM_POSITION_WITH_BALL;
		Long visitorTeamId =  match.getVisitor().getId();
		playground.addPlayer(createPlayer(5L, visitorTeamId), visitorPos.get(5L), false);
		player = createPlayer(6L, visitorTeamId);
		player.setHasBall(!isLocalTeamEngage);
		playground.addPlayer(player, visitorPos.get(6L), false);
		playground.addPlayer(createPlayer(7L, visitorTeamId), visitorPos.get(7L), false);
		playground.addPlayer(createPlayer(8L, visitorTeamId), visitorPos.get(8L), false);

		return playground;
	}

	private static PlayerDetails createPlayer(Long playerId, long teamId) {
		PlayerDetails details = new PlayerDetails();
		details.setId(playerId);
		details.setTeamId(teamId);
		return details;
	}

	public static TileIndex getStartPos(Long playerId, boolean isLocalTeamEngage) {
		Map<Long, TileIndex> localPos = isLocalTeamEngage ? LOCAL_TEAM_POSITION_WITH_BALL : LOCAL_TEAM_POSITION_WITHOU_BALL;
		if (localPos.containsKey(playerId)) {
			return localPos.get(playerId);
		}
		Map<Long, TileIndex> visitorPos = isLocalTeamEngage ? VISITOR_TEAM_POSITION_WITHOU_BALL : VISITOR_TEAM_POSITION_WITH_BALL;
		if (visitorPos.containsKey(playerId)) {
			return visitorPos.get(playerId);
		}
		
		return null;
	}
	
	public static Long getPlayerStartingWithBall(boolean isLocalTeamEngage) {
		return isLocalTeamEngage ? 2L : 6L;
	}
	
	public static TileIndex getBallStartPos(boolean isLocalTeamEngage) {
		Long playerId = getPlayerStartingWithBall(isLocalTeamEngage);
		return isLocalTeamEngage ? LOCAL_TEAM_POSITION_WITH_BALL.get(playerId) : VISITOR_TEAM_POSITION_WITH_BALL.get(playerId);
	}
}
