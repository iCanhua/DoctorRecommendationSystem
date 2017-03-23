package com.scut.adrs.domain.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.Restriction;
import org.springframework.beans.factory.annotation.Autowired;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.util.OntModelUtil;
import com.scut.adrs.util.ontDaoUtils;

public abstract class AbstractOntParserDao {
	
	public static String NS=ontDaoUtils.getNS();
    @Autowired
    protected OntModel model;
    
    public OntModel getModel() {
		return model;
	}
    
    
	public void setModel(OntModel model) {
		this.model = model;
	}
	
	
	//获取所有症状
	public Set<Symptom> getAllSymptom(){
		Set<Symptom> symptomSet=new HashSet<Symptom>();
		OntClass symptomParent= model.getOntClass(NS+"症状");
		for (Iterator<OntClass> i = symptomParent.listSubClasses(); i.hasNext();) {
			Symptom symptom = new Symptom();
			OntClass clazz = i.next();
			symptom.setSymptomName(clazz.getURI());
			symptom.setComment(clazz.getComment(null));
			symptomSet.add(symptom);
		}
		return symptomSet;
	}
	//获取所有症状
	public Set<BodySigns> getAllBodySigns(){
		Set<BodySigns> bodysignSet=new HashSet<BodySigns>();
		OntClass bodysignParent= model.getOntClass(NS+"体征");
		for (Iterator<OntClass> i = bodysignParent.listSubClasses(); i.hasNext();) {
			BodySigns bodysign = new BodySigns(null);
			OntClass clazz = i.next();
			bodysign.setBodySignName(clazz.getURI());
			
			bodysignSet.add(bodysign);
		}
		return bodysignSet;
	}
	public Set<Pathogeny> getAllPathogeny(){
		Set<Pathogeny> pathogenySet=new HashSet<Pathogeny>();
		OntClass pathogenyParent= model.getOntClass(NS+"病因");
		for (Iterator<OntClass> i = pathogenyParent.listSubClasses(); i.hasNext();) {
			OntClass clazz = i.next();
			Pathogeny pathogeny = new Pathogeny(clazz.getURI());
			pathogenySet.add(pathogeny);
		}
		return pathogenySet;
	}
	public Set<Disease> getAllDisease(){
		Set<Disease> diseaseSet=new HashSet<Disease>();
		OntClass diseaseParent= model.getOntClass(NS+"疾病及综合症");
		for (Iterator<OntClass> i = diseaseParent.listSubClasses(); i.hasNext();) {
			OntClass clazz = i.next();
			Disease disease = new Disease(clazz.getURI());
			diseaseSet.add(disease);
		}
		return diseaseSet;
	}
	
	/**
     * 构造约束
     * 通过指定属性和值来获得约束集合；
     */
    public Set<Restriction> getRestriction( String property, String valuesFrom) {
    	if(!property.contains(NS)){
    		property=NS+property;
    	}
    	if(!valuesFrom.contains(NS)){
    		valuesFrom=NS+valuesFrom;
    	}
    	
		Set<Restriction> restrictionSet = new HashSet<Restriction>();
		OntProperty p = model.getOntProperty(property);
		Iterator<Restriction> i = p.listReferringRestrictions();
		while (i.hasNext()) {
			Restriction r = i.next();
			if (r.isSomeValuesFromRestriction()) {
				if (r.asSomeValuesFromRestriction().getSomeValuesFrom().getURI().equals(valuesFrom)) {
					//System.out.println("这里捕捉到一个约束与疾病"+values+"同名");
					restrictionSet.add(r);
				}
			}
			if (r.isAllValuesFromRestriction()) {
				if (r.asAllValuesFromRestriction().getAllValuesFrom().getURI().equals(valuesFrom)) {
					restrictionSet.add(r);
				}
			}
		}
		return restrictionSet;
	}
    public Set<Restriction> getRestriction( String property) {
    	if(!property.contains(NS)){
    		property=NS+property;
    	}
    	
		Set<Restriction> restrictionSet = new HashSet<Restriction>();
		OntProperty p = model.getOntProperty( property);
		Iterator<Restriction> i = p.listReferringRestrictions();
		while (i.hasNext()) {
			Restriction r = i.next();
			if (r.isSomeValuesFromRestriction()) {
				restrictionSet.add(r);
			}
			if (r.isAllValuesFromRestriction()) {
				restrictionSet.add(r);
			}
		}
		return restrictionSet;
	}
}
