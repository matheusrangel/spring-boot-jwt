package com.hackathon.error.handler;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private List<String> errors;

	public Message() { }

	public Message(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void addError(String error) {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		this.errors.add(error);
	}

}
