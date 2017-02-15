package com.scut.adrs.analyticallayer.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.analyticallayer.service.SearchService;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;

@Service
public class SearchServiceImpl implements SearchService {
	private static String NS = "http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#";

	@Autowired
	public OntModel myOntModel;

	@Override
	public Patient buildPatient(String[] symptoms, String[] bodySigns, String[] pathogeny, String[] medicalHistory)
			throws Exception {
		Patient patient = new Patient();
		Set<Symptom> hasSymptoms = new HashSet<>();
		Set<BodySigns> hasBodySigns = new HashSet<>();
		Set<Pathogeny> hasPathogeny = new HashSet<>();
		Set<Disease> hasMedicalHistory = new HashSet<>();
		if (symptoms != null) {
			for (String str : symptoms) {
				OntClass symptomOntClazz = myOntModel.getOntClass(str);
				if (symptomOntClazz == null) {
					throw new Exception("该症状本体类不存在");
				}
				hasSymptoms.add(new Symptom(str));
			}
		}
		if (bodySigns != null) {
			for (String str : bodySigns) {
				OntClass bodySignsOntClazz = myOntModel.getOntClass(str);
				if (bodySignsOntClazz == null) {
					throw new Exception("该体征本体类不存在");
				}
				hasBodySigns.add(new BodySigns(str));
			}
		}
		if (pathogeny != null) {
			for (String str : pathogeny) {
				OntClass pathogenyOntClazz = myOntModel.getOntClass(str);
				if (pathogenyOntClazz == null) {
					throw new Exception("该病因本体类不存在");
				}
				hasPathogeny.add(new Pathogeny(str));
			}
		}
		if (medicalHistory != null) {
			for (String str : medicalHistory) {
				OntClass medicalHistoryOntClazz = myOntModel.getOntClass(str);
				if (medicalHistoryOntClazz == null) {
					throw new Exception("该疾病本体类不存在");
				}
				hasMedicalHistory.add(new Disease(str));
			}
		}
		patient.getHasSymptoms().addAll(hasSymptoms);
		patient.getHasBodySigns().addAll(hasBodySigns);
		patient.getHasPathogeny().addAll(hasPathogeny);
		patient.getHasMedicalHistory().addAll(hasMedicalHistory);
		return patient;
	}

}
