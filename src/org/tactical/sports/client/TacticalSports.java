package org.tactical.sports.client;

import org.tactical.sports.client.activity.AppActivityMapper;
import org.tactical.sports.client.activity.AppPlaceHistoryMapper;
import org.tactical.sports.client.activity.place.ConnectionPlace;
import org.tactical.sports.client.log.Logger;
import org.tactical.sports.client.log.view.LogView;
import org.tactical.sports.client.resources.Res;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class TacticalSports implements EntryPoint {
	
	
	private Place defaultPlace = new ConnectionPlace();
	private SimplePanel appWidget = new SimplePanel();


	public void onModuleLoad() {
		Res.INSTANCE.css().ensureInjected();
		
		Logger logger = Logger.getInstance();
		LogView logView = GWT.create(LogView.class);
		logView.setMainView(appWidget);
		logger.setLogView(logView); 
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);

		RootLayoutPanel.get().add((Widget) logView);
		// Goes to place represented on URL or default place
		historyHandler.handleCurrentHistory();
	}
	
	
}
