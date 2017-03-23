package com.scut.adrs.nlcomprehension.service;
import java.util.HashSet;
import java.util.Set;

import com.scut.adrs.domain.*;
/**
 * 交互问题，该交互问题性质同领域对象中的InterQuestion，拓展了InterQuestion的能力并且新增加用户对描述确定的能力！具体见类构造
 * @author FAN
 *
 */
public class InterConceptQuestion extends InterQuestion{
	private Set<Symptom> Symptoms;//描述中是否拥有症状
	private Set<BodySigns> BodySigns;//描述中是否拥有体征
	private Set<Pathogeny> Pathogeny;//描述中是否拥有病因
	private Set<Disease> MedicalHistory;//描述中是否拥有病史
	
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




