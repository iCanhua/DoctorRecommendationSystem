package com.scut.adrs.recommendation;
import  com.scut.adrs.domain.*;

public interface PreDiagnosis {
	public InterQuestion prediagnosis(Patient patient);
	public Patient prediagnosis(Patient patient,InterQuestion question);

}
