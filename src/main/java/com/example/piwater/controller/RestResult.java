package com.example.piwater.controller;

import java.io.*;

public class RestResult implements Serializable {
	private String message;

	public RestResult(String message) {
		this.message = message;
	}

	public RestResult() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
