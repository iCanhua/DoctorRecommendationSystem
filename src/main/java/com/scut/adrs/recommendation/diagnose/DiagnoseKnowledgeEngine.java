package com.scut.adrs.recommendation.diagnose;

import com.scut.adrs.domain.Patient;

public interface DiagnoseKnowledgeEngine {
	public Patient defineDiseaseIndex(Patient patient);
}
