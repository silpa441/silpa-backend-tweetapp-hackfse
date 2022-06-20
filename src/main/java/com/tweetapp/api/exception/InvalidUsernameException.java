package com.tweetapp.api.exception;

public class InvalidUsernameException extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	public InvalidUsernameException(String msg){
		super(msg);
	}
	
}
