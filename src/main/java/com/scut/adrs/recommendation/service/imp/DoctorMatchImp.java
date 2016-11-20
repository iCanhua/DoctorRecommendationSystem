package com.scut.adrs.recommendation.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.recommendation.service.DocMatchKnowledgeEngine;
import com.scut.adrs.recommendation.service.DoctorMatch;
@Service
public class DoctorMatchImp implements DoctorMatch {
	@Autowired
	DocMatchKnowledgeEngine docMatchKnowledgeEngine;
	
	public DocMatchKnowledgeEngine getDocMatchKnowledgeEngine() {
		return docMatchKnowledgeEngine;
	}

	public void setDocMatchKnowledgeEngine(
			DocMatchKnowledgeEngine docMatchKnowledgeEngine) {
		this.docMatchKnowledgeEngine = docMatchKnowledgeEngine;
	}

	@Override
	public Map<Doctor,Float> doctorMatch(Patient patient) {
		if(patient==null){return null;}
		Map<Doctor,Float> doctorAndIndex= docMatchKnowledgeEngine.doctorMatch(patient);
		return doctorAndIndex;
	}

}
