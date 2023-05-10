package com.neoris.backend.apirest.exceptions;

public class NeorisException extends Exception {

	private String code;
	private String message;
	private String categoryMessage;
	private String action;
	private Integer codeHttp;
	private String messageId;

	public NeorisException(String code, String message, String categoryMessage, String action, Integer codeHttp) {
		this.code = code;
		this.message = message;
		this.categoryMessage = categoryMessage;
		this.action = action;
		this.codeHttp = codeHttp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategoryMessage() {
		return categoryMessage;
	}

	public void setCategoryMessage(String categoryMessage) {
		this.categoryMessage = categoryMessage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getCodeHttp() {
		return codeHttp;
	}

	public void setCodeHttp(Integer codeHttp) {
		this.codeHttp = codeHttp;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}

