package com.scut.adrs.analyticallayer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.analyticallayer.service.PatientService;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.InterQuestion;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.RecommendationProxy;
import com.scut.adrs.util.QuestionUtil;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	RecommendationProxy proxy;

	@Override
	public List<Question> getQuestionByPatient(Patient patient) {
		InterQuestion interQuestion = null;
		List<Question> questionList = new ArrayList<Question>();
		try {
			interQuestion = proxy.prediagnosis(patient);
			questionList.addAll(QuestionUtil.getSymptomQuestion(interQuestion.getHasSymptoms()));
			questionList.addAll(QuestionUtil.getBodySigns(interQuestion.getHasBodySigns()));
			questionList.addAll(QuestionUtil.getPathogenyQuestion(interQuestion.getHasPathogeny()));
			questionList.addAll(QuestionUtil.getMedicalHistoryQuestion(interQuestion.getHasMedicalHistory()));
		} catch (UnExistURIException e) {
			e.printStackTrace();
		}
		return questionList;
	}

}
