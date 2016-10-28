package com.scut.adrs.model;

public class Doctor {
	public String name;
	public String profession;
	public String field;
	public String expert;

	public Doctor(String name, String profession, String field, String expert) {
		super();
		this.name = name;
		this.profession = profession;
		this.field = field;
		this.expert = expert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

}
