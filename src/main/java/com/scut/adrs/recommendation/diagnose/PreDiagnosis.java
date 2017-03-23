package com.scut.adrs.recommendation.diagnose;
import  com.scut.adrs.domain.*;
import com.scut.adrs.recommendation.exception.UnExistURIException;

public interface PreDiagnosis {
	public InterQuestion prediagnosis(Patient patient) throws UnExistURIException;
	public Patient prediagnosis(Patient patient,InterQuestion question);

}
