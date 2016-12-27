package com.scut.adrs.analyticallayer.service.impl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.analyticallayer.dao.DoctorDao;
import com.scut.adrs.analyticallayer.service.DoctorService;
import com.scut.adrs.domain.Doctor;

@Service
public class DoctorServiceImpl implements DoctorService {
	public static String prefix = "PREFIX  untitled-ontology-26: <http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#>"
			+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX  owl: <http://www.w3.org/2002/07/owl#>" + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	public static String NS = "http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#";

	@Autowired
	public OntModel myOntModel;

	@Autowired
	public DoctorDao doctorDao;

	@Override
	public String getIntroduction(String uri) {
		Individual individual = myOntModel.getIndividual(uri);
		return individual.getComment(null);
	}

	@Override
	public Doctor getDoctorData(String name) {
		return doctorDao.getDoctor(name);
	}

}
