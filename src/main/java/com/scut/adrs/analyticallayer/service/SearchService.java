package com.scut.adrs.analyticallayer.service;

import com.scut.adrs.domain.Patient;
import com.scut.adrs.nlcomprehension.service.InterConceptQuestion;

public interface SearchService {

	public Patient buildPatient(String[] symptoms, String[] bodySigns, String[] pathogeny, String[] medicalHistory,
			Patient patient) throws Exception;

	public Patient buildPatient(InterConceptQuestion interConceptQuestion, Patient patient);
}
