package org.tactical.sports.client.view.helper;

import org.tactical.sports.client.activity.presenter.Presenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface ViewWithPresenter<T extends Presenter<? extends IsWidget>> extends IsWidget {
	void setPresenter(T listener);
}
