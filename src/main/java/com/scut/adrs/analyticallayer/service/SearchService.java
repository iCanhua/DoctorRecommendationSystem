package com.scut.adrs.analyticallayer.service;

import com.scut.adrs.domain.Patient;

public interface SearchService {

	public Patient buildPatient(String[] symptoms, String[] bodySigns, String[] pathogeny, String[] medicalHistory)
			throws Exception;
}
