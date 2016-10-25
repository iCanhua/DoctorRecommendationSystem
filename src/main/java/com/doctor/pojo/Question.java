package com.doctor.pojo;

import java.util.List;

public class Question {
	String description;
	List<String> choices;

	public Question(String description, List<String> choices) {
		super();
		this.description = description;
		this.choices = choices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

}
