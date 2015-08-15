package org.tactical.sports.client.resources;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface Res extends ClientBundle {
	Res INSTANCE = GWT.create(Res.class);
	
	@Source("img/hexa.png")
	ImageResource hexa();
	
	@Source("img/hexaGoal.png")
	ImageResource hexaGoal();

	@Source("img/hexaZone.png")
	ImageResource hexaZone();
	
	@Source("img/hexaPath.png")
	ImageResource hexaPath();
	
	@Source("img/playgroundLines.png")	
	DataResource playgroundLines();

	@Source("/img/ball.png")
	ImageResource ball();

	@Source("/img/playerAnimations.png")
	ImageResource playerAnimations();

	@Source("/img/visitorPlayerAnimations.png")
	ImageResource visitorPlayerAnimations();
	
	@Source("img/footPGBackground.png")
	@ImageOptions(repeatStyle = RepeatStyle.Both)
	ImageResource footPGBackground();
	
	@Source("css/InnerTacticalSports.css")
	CSS css();
}
