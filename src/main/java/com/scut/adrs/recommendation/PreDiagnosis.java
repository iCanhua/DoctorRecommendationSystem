package com.scut.adrs.recommendation;
import  com.scut.adrs.domain.*;
import com.scut.adrs.recommendation.exception.UnExistRdfException;

public interface PreDiagnosis {
	public InterQuestion prediagnosis(Patient patient) throws UnExistRdfException;
	public Patient prediagnosis(Patient patient,InterQuestion question);

}
