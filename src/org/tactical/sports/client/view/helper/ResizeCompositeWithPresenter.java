package org.tactical.sports.client.view.helper;

import org.tactical.sports.client.activity.presenter.Presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ResizeCompositeWithPresenter<T extends Presenter<? extends IsWidget>> extends ResizeComposite implements ViewWithPresenter<T> {

	private T m_presenter;
	
	public void setPresenter(T presenter) {
		m_presenter = presenter;
	}

	protected T getPresenter() {
		return m_presenter;
	}

}
