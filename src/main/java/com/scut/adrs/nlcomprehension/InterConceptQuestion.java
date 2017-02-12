package com.scut.adrs.nlcomprehension;
import java.util.HashSet;
import java.util.Set;

import com.scut.adrs.domain.*;

public class InterConceptQuestion extends InterQuestion{
	private Set<Symptom> Symptoms;//是否拥有症状
	private Set<BodySigns> BodySigns;//是否拥有体征
	private Set<Pathogeny> Pathogeny;//是否拥有病因
	private Set<Disease> MedicalHistory;//是否拥有病史
	
	public InterConceptQuestion() {
		super();
		super.hasSymptoms=new HashSet<Symptom>();
		super.hasBodySigns=new HashSet<BodySigns>();
		super.hasMedicalHistory=new HashSet<Disease>();
		super.hasPathogeny=new HashSet<Pathogeny>();
		this.BodySigns=new HashSet<BodySigns>();
		this.MedicalHistory=new HashSet<Disease>();
		this.Pathogeny=new HashSet<Pathogeny>();
		this.Symptoms=new HashSet<Symptom>();
	}
	public Set<Symptom> getSymptoms() {
		return Symptoms;
	}
	public void setSymptoms(Set<Symptom> symptoms) {
		Symptoms = symptoms;
	}
	public Set<BodySigns> getBodySigns() {
		return BodySigns;
	}
	public void setBodySigns(Set<BodySigns> bodySigns) {
		BodySigns = bodySigns;
	}
	public Set<Pathogeny> getPathogeny() {
		return Pathogeny;
	}
	public void setPathogeny(Set<Pathogeny> pathogeny) {
		Pathogeny = pathogeny;
	}
	public Set<Disease> getMedicalHistory() {
		return MedicalHistory;
	}
	public void setMedicalHistory(Set<Disease> medicalHistory) {
		MedicalHistory = medicalHistory;
	}
	
}




