package org.tactical.sports.client.view;

import org.tactical.sports.client.activity.presenter.Presenter;
import org.tactical.sports.client.view.ConnectionView.ConnectionPresenter;
import org.tactical.sports.client.view.helper.ViewWithPresenter;

public interface ConnectionView extends ViewWithPresenter<ConnectionPresenter> {

	void setUser(String name, String pass);
	void showError(String error);

	public interface ConnectionPresenter extends Presenter<ConnectionView> {
		void register(String name, String pass);
		void login(String name, String pass);
	}
}
