package org.tactical.sports.client.view;

import java.util.List;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.MatchChooserView.MatchChooserPresenter;
import org.tactical.sports.client.view.helper.CompositeWithPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.CaptionPanel;

public class MatchChooserViewImpl extends CompositeWithPresenter<MatchChooserPresenter> implements MatchChooserView {

	private static MatchChooserViewImplUiBinder uiBinder = GWT.create(MatchChooserViewImplUiBinder.class);
	@UiField
	VerticalPanel m_matchList;
	@UiField
	Button m_createMatchButton;
	@UiField Label m_emptyListLabel;
	@UiField CaptionPanel m_matchPanel;

	interface MatchChooserViewImplUiBinder extends UiBinder<Widget, MatchChooserViewImpl> {
	}

	public MatchChooserViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setMatchList(List<String> matchlist) {
		m_matchList.clear();
		m_emptyListLabel.setVisible(false);
		m_matchPanel.setVisible(true);
		for (final String match : matchlist) {
			Label matchLabel = new Label(match);
			matchLabel.setWidth("100%");
			matchLabel.setStyleName(Res.INSTANCE.css().centerlabel());
			matchLabel.getElement().getStyle().setProperty("width", "auto");
			m_matchList.add(matchLabel);
			matchLabel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					getPresenter().onMatchChoosen(match);
				}
			});
		}
	}

	@UiHandler("m_createMatchButton")
	void onCreateMatch(ClickEvent event) {
		getPresenter().onMatchCreated();
	}

	@Override
	public void setEmptyMessage(String message) {
		m_emptyListLabel.setText(message);
		m_emptyListLabel.setVisible(true);
		m_matchPanel.setVisible(false);
	}
}
