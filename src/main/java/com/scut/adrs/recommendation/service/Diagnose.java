package com.scut.adrs.recommendation.service;

import com.scut.adrs.domain.Patient;

public interface Diagnose {
	/**
	 * 为病人诊断，建立患病模型，该方法仅诊断，不被推荐模块依赖
	 * @param patient
	 * @return
	 */
	public Patient diagnose(Patient patient);
	
}
