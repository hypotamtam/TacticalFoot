package org.tactical.sports.client.view.animation;

import org.tactical.sports.client.view.animatedwidget.animation.AbstractAnimation;
import org.tactical.sports.client.view.animatedwidget.animation.Animation;
import org.tactical.sports.client.view.animatedwidget.animation.Animation.Callback;

import com.google.gwt.junit.client.GWTTestCase;


public class AbstractAnimationTest extends GWTTestCase {
	
	private boolean m_hasCallsCallback;
	
	public void testStopCallsOnAnimationFinish() {
		final AbstractAnimation animationTest = new FakeAbstractAnimation();
		m_hasCallsCallback = false;
		animationTest.play(0, new Callback() {
			
			@Override
			public void onAniationUpdated(Animation animation, double progress) {
				// TODO Auto-generated method stub
			}
		
			@Override
			public void onAniationFinished(Animation animation) {
				assertEquals(animationTest, animation);
				m_hasCallsCallback = true;
			}
		});
		animationTest.stop();
		assertTrue(m_hasCallsCallback);
	}
	


	@Override
	public String getModuleName() {
		return "org.tactical.sports.TacticalSports";
	}

}
