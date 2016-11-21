package com.scut.adrs.recommendation.service;

import com.scut.adrs.domain.Patient;

public interface DiagnoseKnowledgeEngine {
	public Patient defineDiseaseIndex(Patient patient);
}
