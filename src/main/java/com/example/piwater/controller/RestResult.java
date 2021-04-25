package com.example.piwater.controller;

import java.io.*;

public class RestResult implements Serializable {
	private int status;
	private Object object;

	public RestResult(int status, Object object) {
		this.status = status;
		this.object = object;
	}

	public int getStatus() {
		return status;
	}

	public Object getObject() {
		return object;
	}
}
