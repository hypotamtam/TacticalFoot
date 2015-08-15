package org.tactical.sports.client.view.action;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.view.action.GameActionMenu.GameActionMenuPresenter;
import org.tactical.sports.client.view.helper.CompositeWithPresenter;
import org.tactical.sports.client.view.playground.grid.GridPosition;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.grid.GridPositionHelperFactory;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.rule.action.ActionType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GameActionImpl extends CompositeWithPresenter<GameActionMenuPresenter> implements GameActionMenu {

	private static GameActionUiBinder uiBinder = GWT.create(GameActionUiBinder.class);
	@UiField HorizontalPanel m_actionPanel;
	@UiField Button m_validateButton;
	@UiField HTMLPanel m_mainPanel;

	private Map<ActionType, Button> m_actionButtonByType;
	private GridPositionHelper m_positionHelper;

	interface GameActionUiBinder extends UiBinder<Widget, GameActionImpl> {
	}

	public GameActionImpl() {
		m_actionButtonByType = new HashMap<ActionType, Button>();
		m_positionHelper = GridPositionHelperFactory.create();	
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("m_validateButton")
	void onValidateButtonClick(ClickEvent event) {
		getPresenter().onActionValidated();
	}
	
	@Override
	public void clearGameActions() {
		m_actionButtonByType.clear();
		m_actionPanel.clear();	
	}
	
	
	@Override
	public void addGameAction(final ActionType action) {
		final GameActionMenuPresenter presenter = getPresenter();
		Button actionButton = new Button(action.name());
		actionButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.onActionSelected(action);
			}
		});
		m_actionButtonByType.put(action, actionButton);
		m_actionPanel.add(actionButton);
	}

	@Override
	public void setEnableGameAction(ActionType action, boolean isEnable) {
		m_actionButtonByType.get(action).setEnabled(isEnable);
	}

	@Override
	public void showActionChooser(boolean show, TileIndex pos) {
		m_actionPanel.setVisible(show);
		m_validateButton.setVisible(false);
		updateMenuPosition(pos);
	}

	@Override
	public void showActionValidation(boolean show, TileIndex pos) {		
		m_validateButton.setVisible(show);
		m_actionPanel.setVisible(false);
		updateMenuPosition(pos);
	}

	private void updateMenuPosition(TileIndex pos) {
		GridPosition position = m_positionHelper.getGridPosition(pos);
		int x = position.getLeft() + (m_positionHelper.getGridElementWidth() -  m_mainPanel.getOffsetWidth()) / 2;
		int y = position.getTop() -  m_mainPanel.getOffsetHeight();
		Style style = m_mainPanel.getElement().getStyle();
		style.setLeft(x, Unit.PX);
		style.setTop(y, Unit.PX);
	}

}
