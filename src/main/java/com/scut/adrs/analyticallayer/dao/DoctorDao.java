package com.scut.adrs.analyticallayer.dao;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scut.adrs.domain.Doctor;
import com.scut.adrs.util.OntModelUtil;

@Repository
public class DoctorDao {
	public static String prefix = "PREFIX  untitled-ontology-26: <http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#>"
			+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX  owl: <http://www.w3.org/2002/07/owl#>" + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	@Autowired
	OntModel myOntModel;

	public Doctor getDoctor(String name) {
		Doctor doctor = new Doctor();
		doctor.setName(name);

		// 介绍
		Set<Literal> literalSet = OntModelUtil.processARQGetLiteral(myOntModel, OntModelUtil.getComment(name));
		String introduction = literalSet.iterator().next().getString();
		doctor.setIntroduction(introduction);

		// class assertion
		Set<Individual> shanchangType = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getTypes(name, "擅长"));
		Set<Individual> youqishanchangType = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getTypes(name, "尤其擅长"));

		// object property assertion
		Set<Individual> shanchangProperty = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getProperty(name, "擅长"));
		Set<Individual> youqishanchangProperty = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getProperty(name, "尤其擅长"));

		Set<String> expertSet = new HashSet<String>();
		for (Individual i : shanchangType) {
			expertSet.add(i.getLocalName());
		}
		for (Individual i : youqishanchangType) {
			expertSet.add(i.getLocalName());
		}

		Set<String> sexpertSet = new HashSet<String>();
		for (Individual i : shanchangProperty) {
			sexpertSet.add(i.getLocalName());
		}
		for (Individual i : youqishanchangProperty) {
			sexpertSet.add(i.getLocalName());
		}

		// object property assertion
		Set<Individual> titleProperty = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getProperty(name, "被评价"));
		Set<String> titleSet = new HashSet<String>();
		for (Individual i : titleProperty) {
			titleSet.add(i.getLocalName());
		}

		// object property assertion
		Set<Individual> groupProperty = OntModelUtil.processARQGetIndividual(myOntModel,
				OntModelUtil.getProperty(name, "是成员"));
		Set<String> groupSet = new HashSet<String>();
		for (Individual i : groupProperty) {
			groupSet.add(i.getLocalName());
		}

		doctor.setGroup(groupSet);
		doctor.setExpert(expertSet);
		doctor.setSexpert(sexpertSet);
		doctor.setTitle(titleSet);
		return doctor;
	}

}
