package org.tactical.sports.client.view.tiles;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.tactical.sports.client.view.grid.GridElementModel;

import com.google.gwt.resources.client.ImageResource;

public class TileModelTests extends TestCase {

	public void testGetWidthHeightReturnsTheResourceSize() {
		ImageResource resource = mock(ImageResource.class);
		when(resource.getWidth()).thenReturn(32);
		when(resource.getHeight()).thenReturn(18);
		
		GridElementModel model = new GridElementModel(resource);
		assertEquals(32, model.getWidth());
		assertEquals(18, model.getHeight());
	}
	
	public void testGetResourceReturnsTheInitResource() {
		ImageResource resource = mock(ImageResource.class);
		
		GridElementModel model = new GridElementModel(resource);
		assertEquals(resource, model.getResource());
	}
	
}
