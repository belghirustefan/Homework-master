package com.example.homework.base;

public abstract class BaseApiInterface {
	
	protected BaseApiListener apiListener;
	
	/** sets the api listener for an activity so it could listen to the events in the controller */
	public void setApiListener(BaseApiListener listener){
		apiListener = listener;
	}
	
	
	
}
