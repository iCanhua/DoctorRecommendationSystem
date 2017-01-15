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
	public Patient searchInfo(Set<String> infos) {
		OntClass rootSymptom = myOntModel.getOntClass(NS + "症状");
		OntClass rootBodySigns = myOntModel.getOntClass(NS + "体征");
		OntClass rootPathogeny = myOntModel.getOntClass(NS + "病因");
		OntClass rootDisease = myOntModel.getOntClass(NS + "疾病及综合症");
		Set<Symptom> hasSymptoms = new HashSet<>();
		Set<BodySigns> hasBodySigns = new HashSet<>();
		Set<Pathogeny> hasPathogeny = new HashSet<>();
		Set<Disease> hasMedicalHistory = new HashSet<>();
		Patient patient = new Patient();
		for (String info : infos) {
			OntClass clazz = myOntModel.getOntClass(NS + info);
			if (clazz == null) {
				continue;
			}
			if (clazz.hasSuperClass(rootSymptom)) {
				hasSymptoms.add(new Symptom(NS + info));
			}
			if (clazz.hasSuperClass(rootBodySigns)) {
				hasBodySigns.add(new BodySigns(NS + info));
			}
			if (clazz.hasSuperClass(rootPathogeny)) {
				hasPathogeny.add(new Pathogeny(NS + info));
			}
			if (clazz.hasSuperClass(rootDisease)) {
				hasMedicalHistory.add(new Disease(NS + info));
			}
		}
		patient.getHasSymptoms().addAll(hasSymptoms);
		patient.getHasBodySigns().addAll(hasBodySigns);
		patient.getHasPathogeny().addAll(hasPathogeny);
		patient.getHasMedicalHistory().addAll(hasMedicalHistory);
		return patient;
	}

}
