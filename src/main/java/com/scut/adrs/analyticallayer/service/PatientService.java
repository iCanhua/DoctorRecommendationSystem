package com.scut.adrs.analyticallayer.service;

import java.util.List;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.domain.Patient;

public interface PatientService {
	/**
	 * 
	 * @param patient
	 * @return
	 * @Description 根据病人信息提取问卷
	 */
	public List<Question> getQuestionByPatient(Patient patient);
}
