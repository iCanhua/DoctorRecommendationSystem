package com.scut.adrs.analyticallayer.service;

import java.util.List;

import com.scut.adrs.domain.Symptom;

public interface SymptomService {
	public List<Symptom> getSymptomList(String position);

}
