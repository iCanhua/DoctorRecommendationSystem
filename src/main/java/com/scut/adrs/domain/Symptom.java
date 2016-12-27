package com.scut.adrs.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

public class Symptom implements Resourse {
	/**
	 * 一般用RDF表示
	 */
	String symptomName; // RDF名字
	Set<String> room;

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

	// 重写该方法，便于加入相同的症状
	public boolean equals(Object obj) {
		if (!(obj instanceof Symptom)) {
			return false;
		} else {
			Symptom sp = (Symptom) obj;
			if (sp.getSymptomName().equals(this.symptomName)) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return this.symptomName.hashCode();
	}

	public static void main(String[] args) {
		Symptom a = new Symptom("123");
		Symptom b = new Symptom("123");
		Set<Symptom> set = new HashSet<Symptom>();
		set.add(b);
		set.add(a);
		System.out.println("大小" + set.size());
	}

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.symptomName;
	}

}
