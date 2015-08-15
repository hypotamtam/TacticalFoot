package org.tactical.sports.shared.services.error;

import java.io.Serializable;

public class UserAlreadyExistsException extends Throwable implements Serializable{

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super();
	}
	
	
}
