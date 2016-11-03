package com.scut.adrs.domain;

import java.util.List;

import org.apache.jena.ontology.OntClass;

public class Patient {
	String name;
	String sex;
	Integer age;
	OntClass symptom;
	OntClass pathogeny;
	List<OntClass> medicalHistory;

	public Patient() {
		super();
	}

	public Patient(String name, String sex, Integer age, OntClass symptom,
			OntClass pathogeny, List<OntClass> medicalHistory) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.symptom = symptom;
		this.pathogeny = pathogeny;
		this.medicalHistory = medicalHistory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public OntClass getSymptom() {
		return symptom;
	}

	public void setSymptom(OntClass symptom) {
		this.symptom = symptom;
	}

	public OntClass getPathogeny() {
		return pathogeny;
	}

	public void setPathogeny(OntClass pathogeny) {
		this.pathogeny = pathogeny;
	}

	public List<OntClass> getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(List<OntClass> medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

}
