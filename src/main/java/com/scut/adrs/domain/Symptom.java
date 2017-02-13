package com.scut.adrs.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

public class Symptom extends AbstractConcept {
	/**
	 * 一般用RDF表示
	 */
	String symptomName; // RDF名字
	Set<String> room;
	String comment;
	public Symptom() {

	}
	public Symptom(String uri) {
		super();
		this.symptomName = uri;
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

	public Set<String> getRoom() {
		return room;
	}

	public void setRoom(Set<String> room) {
		this.room = room;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public static void main(String[] args) {
		Symptom a = new Symptom("123");
		Symptom b = new Symptom("123");
		Set<Symptom> set = new HashSet<Symptom>();
		set.add(b);
		set.add(a);
		System.out.println("症状大小" + set.size());
	}

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.symptomName;
	}

	@Override
	public String getDomainType() {
		// TODO Auto-generated method stub
		return "症状";
	}

}
