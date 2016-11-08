package com.scut.adrs.domain;

import org.apache.jena.ontology.OntClass;

public class Symptom {
	/**
	 * 一般用RDF表示
	 */
	String symptomName; //RDF名字


	public Symptom(String uri) {
		super();
		this.symptomName=uri;
	}

	public String getSymptomName() {
		return symptomName;
	}

	public void setSymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
	
	public void setSymptomName(OntClass ontSymptomName) {
		this.symptomName = ontSymptomName.toString();
	}

	
	
	
	
}
