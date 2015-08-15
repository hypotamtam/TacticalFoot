package org.tactical.sports.client.view;

import org.tactical.sports.client.view.ConnectionView.ConnectionPresenter;
import org.tactical.sports.client.view.helper.CompositeWithPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class ConnectionViewImpl extends CompositeWithPresenter<ConnectionPresenter> implements ConnectionView {

	private static ConnectionViewImplUiBinder uiBinder = GWT.create(ConnectionViewImplUiBinder.class);
	@UiField TextBox m_loginNameTextBox;
	@UiField PasswordTextBox m_loginPassTextBox;
	@UiField Label m_errorLabel;
	@UiField Button m_loginButton;
	@UiField Button m_registerButton;
	@UiField TextBox m_registerNameTextBox;
	@UiField PasswordTextBox m_registerPassTextBox;
	
	interface ConnectionViewImplUiBinder extends UiBinder<Widget, ConnectionViewImpl> {
	}

	public ConnectionViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("m_loginButton")
	void onLoginButtonClick(ClickEvent event) {
		m_errorLabel.setVisible(false);
		getPresenter().login(m_loginNameTextBox.getText(), m_loginPassTextBox.getText());
	}
		
	@UiHandler("m_registerButton")
	void onRegisterButtonClick(ClickEvent event) {
		m_errorLabel.setVisible(false);
		getPresenter().register(m_registerNameTextBox.getText(), m_registerPassTextBox.getText());
	}

	@Override
	public void showError(String error) {
		m_errorLabel.setText(error);
		m_errorLabel.setVisible(true);
	}

	@Override
	public void setUser(String name, String pass) {
		m_loginNameTextBox.setText(name);
		m_registerNameTextBox.setText(name);
		m_loginPassTextBox.setText(pass);
		m_registerPassTextBox.setText(pass);
	}
	
}
