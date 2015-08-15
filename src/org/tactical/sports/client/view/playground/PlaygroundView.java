package org.tactical.sports.client.view.playground;

import java.util.Collection;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;
import org.tactical.sports.client.view.playground.PlaygroundView.PlaygroundPresenter;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;

/**
 * View base interface.
 * Extends IsWidget so a view impl can easily provide its container widget.
 */
public interface PlaygroundView extends ViewWithPresenter<PlaygroundPresenter> {
  
	// TODO: à voir -> laisser le display se redimensionner automatiquement
	void setSize(int columnCount, int rowCount);
	
	void setSpecialTile(TileIndex pos, TileType type);
	void addPlayer(long playerId, TileIndex pos, boolean isLocal, boolean ishighlighable);
	void setBall(TileIndex pos);
	
	void prepareAnimation();
	void movePlayer(long playerId, TileIndex destination);
	void moveBall(TileIndex destination);
	void startAnimation(int duration);
	
	void initActionDefinition(Collection<TileIndex> actionPossibility);
	void updateActionDefinition(Collection<TileIndex> actionPossibility);
	void finishActionDefinition();
	
	void setAction(Collection<TileIndex> actionArea, long playerId);
	void removeAction(long playerId);
	void clearActions();
	
	
	public interface PlaygroundPresenter extends Presenter<PlaygroundView> {
		
		void onClick(TileIndex index);	
		
		void onMouseOver(TileIndex index);
		void onMouseOut();
		
		void onAnimationEnded();
		
	}
}
