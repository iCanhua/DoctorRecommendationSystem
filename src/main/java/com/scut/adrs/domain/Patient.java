package com.scut.adrs.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Patient {
	
	private String name;
	private String sex;
	private Integer age;
	private boolean isPreDiagnosis=false;
	private Set<Symptom> hasSymptoms;//拥有症状
	private Set<BodySigns> hasBodySigns;//拥有体征
	private Set<Pathogeny> hasPathogeny;//可能造成病因的病因
	private Set<Disease> hasMedicalHistory;//病史
	private Map<Disease,Float>  DiseaseAndIndex;//可能疾病及其概率
	
	public Patient(){
		this.hasSymptoms=new HashSet<Symptom>();
		this.hasBodySigns=new HashSet<BodySigns>(); 
		this.hasPathogeny=new HashSet<Pathogeny>();
		this.hasMedicalHistory=new HashSet<Disease>();
		this.DiseaseAndIndex=new HashMap<Disease, Float>();
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
	public boolean isPreDiagnosis() {
		return isPreDiagnosis;
	}
	public void setPreDiagnosis(boolean isPreDiagnosis) {
		this.isPreDiagnosis = isPreDiagnosis;
	}
	public Set<Symptom> getHasSymptoms() {
		return hasSymptoms;
	}
	public void setHasSymptoms(Set<Symptom> hasSymptoms) {
		this.hasSymptoms = hasSymptoms;
	}
	public Set<BodySigns> getHasBodySigns() {
		return hasBodySigns;
	}
	public void setHasBodySigns(Set<BodySigns> hasBodySigns) {
		this.hasBodySigns = hasBodySigns;
	}
	public Set<Pathogeny> getHasPathogeny() {
		return hasPathogeny;
	}
	public void setHasPathogeny(Set<Pathogeny> hasPathogeny) {
		this.hasPathogeny = hasPathogeny;
	}
	public Set<Disease> getHasMedicalHistory() {
		return hasMedicalHistory;
	}
	public void setHasMedicalHistory(Set<Disease> hasMedicalHistory) {
		this.hasMedicalHistory = hasMedicalHistory;
	}
	public Map<Disease, Float> getDiseaseAndIndex() {
		return DiseaseAndIndex;
	}
	public void setDiseaseAndIndex(Map<Disease, Float> diseaseAndIndex) {
		DiseaseAndIndex = diseaseAndIndex;
	}
	
	public void addDisease(Disease disease,Float index){
		this.DiseaseAndIndex.put(disease, index);
	}
	
	public void addSymptom(String rdf){
		hasSymptoms.add(new Symptom(rdf));
	}

}
