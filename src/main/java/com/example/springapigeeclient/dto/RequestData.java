package com.example.springapigeeclient.dto;

public class RequestData {
	
	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return "RequestData [input=" + input + "]";
	}
	
}
