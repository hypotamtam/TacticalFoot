package org.tactical.sports.client.view;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.tactical.sports.client.view.grid.GridElementModel;
import org.tactical.sports.client.view.grid.GridIndex;

import com.google.gwt.resources.client.ImageResource;


public class PlaygroundManagerTest extends TestCase{

	public void testGetWidthAndGetHeight() {
		PlaygroundManager playgroundManager = new PlaygroundManager();
		ImageResource resource = Mockito.mock(ImageResource.class);
		Mockito.when(resource.getHeight()).thenReturn(2);
		Mockito.when(resource.getWidth()).thenReturn(2);
		GridElementModel model = new GridElementModel(resource);
		playgroundManager.setGridElementModel(model);
		
		assertEquals(20, playgroundManager.getHeight(10));
		assertEquals(15, playgroundManager.getWidth(10));
	}
	
	public void testGetGridIndexFromPosition() {
		PlaygroundManager playgroundManager = new PlaygroundManager();
		ImageResource resource = Mockito.mock(ImageResource.class);
		Mockito.when(resource.getHeight()).thenReturn(56);
		Mockito.when(resource.getWidth()).thenReturn(64);
		GridElementModel model = new GridElementModel(resource);
		playgroundManager.setGridElementModel(model);

		GridIndex index = playgroundManager.getGridIndexFromPosition(resource.getWidth(), resource.getHeight());
		assertEquals(new GridIndex(1, 1), index);
		index = playgroundManager.getGridIndexFromPosition(0, 0);
		assertEquals(new GridIndex(0, -1), index);
		index = playgroundManager.getGridIndexFromPosition(755, 26);
		assertEquals(new GridIndex(15, 0), index);
		index = playgroundManager.getGridIndexFromPosition(487, 276);
		assertEquals(new GridIndex(10, 4), index);
		index = playgroundManager.getGridIndexFromPosition(507, 278);
		assertEquals(new GridIndex(10, 4), index);
		index = playgroundManager.getGridIndexFromPosition(781, 474);
		assertEquals(new GridIndex(15, 8), index);
	}
}
