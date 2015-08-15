package org.tactical.sports.client.view.action;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.action.GameActionMenu.GameActionMenuPresenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.rule.action.ActionType;

public interface GameActionMenu extends ViewWithPresenter<GameActionMenuPresenter> {
	
	void clearGameActions();
	void addGameAction(ActionType action);
	void setEnableGameAction(ActionType action, boolean isEnable);
	
	void showActionChooser(boolean show, TileIndex pos);
	void showActionValidation(boolean show, TileIndex pos);
	
	public interface GameActionMenuPresenter extends Presenter<GameActionMenu> {
		
		void onActionSelected(ActionType actions);
		void onActionValidated();
		void onActionCanceled();
	}

}
