package org.tactical.sports.client.network;

public interface FutureListener<T> {

	void onDone(Future<T> future);
	
}
