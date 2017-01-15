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
