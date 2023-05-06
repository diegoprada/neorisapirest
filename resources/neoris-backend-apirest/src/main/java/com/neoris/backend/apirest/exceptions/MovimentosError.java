package com.neoris.backend.apirest.exceptions;

import org.springframework.http.HttpStatus;

public class MovimentosError extends Exception {

	private String message;
	private HttpStatus codeHttp;

	public MovimentosError(String message, HttpStatus codeHttp) {
		this.message = message;
		this.codeHttp = codeHttp;

	}

	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public HttpStatus getCodeHttp() {
		return codeHttp;
	}


	public void setCodeHttp(HttpStatus codeHttp) {
		this.codeHttp = codeHttp;
	}

	

}
