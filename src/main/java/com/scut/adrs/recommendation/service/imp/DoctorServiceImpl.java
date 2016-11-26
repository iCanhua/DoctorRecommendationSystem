package com.scut.adrs.recommendation.service.imp;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.recommendation.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	public OntModel myOntModel;

	@Override
	public String getIntroduction(String uri) {
		Individual individual = myOntModel.getIndividual(uri);
		return individual.getComment(null);
	}

}
