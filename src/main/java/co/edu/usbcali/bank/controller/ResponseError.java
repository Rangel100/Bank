package co.edu.usbcali.bank.controller;

public class ResponseError {

	String code;
	String message;

	public ResponseError() {
		super();
	}

	public ResponseError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
