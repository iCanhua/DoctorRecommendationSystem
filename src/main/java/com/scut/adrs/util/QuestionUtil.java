package com.scut.adrs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;

public class QuestionUtil {
	public static List<Question> getSymptomQuestion(Set<Symptom> symptomSet) {
		int i = 0;
		List<Question> questionList = new ArrayList<Question>();
		Question question = null;
		if (symptomSet.size() == 0) {
			return questionList;
		} else {
			question = new Question();
			question.setChoices(new ArrayList<String>());
			question.setDescription("您是否有以下症状");
			question.setType(1);
			questionList.add(question);
		}
		for (Symptom symptom : symptomSet) {
			question.getChoices().add(symptom.getSymptomName().split("#")[1]);
			i++;
			if (i % 10 == 0) {
				question = new Question();
				question.setChoices(new ArrayList<String>());
				question.setDescription("您是否有以下症状");
				questionList.add(question);
			}
		}
		if (questionList.get(questionList.size() - 1).getChoices().size() == 0) {
			questionList.remove(questionList.size() - 1);
		}
		return questionList;
	}

	public static List<Question> getBodySigns(Set<BodySigns> bodySignsSet) {
		int i = 0;
		List<Question> questionList = new ArrayList<Question>();
		Question question = null;
		if (bodySignsSet.size() == 0) {
			return questionList;
		} else {
			question = new Question();
			question.setChoices(new ArrayList<String>());
			question.setDescription("您是否有以下体征");
			question.setType(2);
			questionList.add(question);
		}
		for (BodySigns bodySigns : bodySignsSet) {
			question.getChoices()
					.add(bodySigns.getBodySignName().split("#")[1]);
			i++;
			if (i % 10 == 0) {
				question = new Question();
				question.setChoices(new ArrayList<String>());
				question.setDescription("您是否有以下体征");
				questionList.add(question);
			}
		}
		if (questionList.get(questionList.size() - 1).getChoices().size() == 0) {
			questionList.remove(questionList.size() - 1);
		}
		return questionList;
	}

	public static List<Question> getPathogenyQuestion(
			Set<Pathogeny> pathogenySet) {
		int i = 0;
		List<Question> questionList = new ArrayList<Question>();
		Question question = null;
		if (pathogenySet.size() == 0) {
			return questionList;
		} else {
			question = new Question();
			question.setChoices(new ArrayList<String>());
			question.setDescription("您是否有以下病因");
			question.setType(3);
			questionList.add(question);
		}
		for (Pathogeny pathogeny : pathogenySet) {
			question.getChoices().add(
					pathogeny.getPathogenyName().split("#")[1]);
			i++;
			if (i % 10 == 0) {
				question = new Question();
				question.setChoices(new ArrayList<String>());
				question.setDescription("您是否有以下病因");
				questionList.add(question);
			}
		}
		if (questionList.get(questionList.size() - 1).getChoices().size() == 0) {
			questionList.remove(questionList.size() - 1);
		}
		return questionList;
	}

	public static List<Question> getMedicalHistoryQuestion(
			Set<Disease> medicalHistorySet) {
		int i = 0;
		List<Question> questionList = new ArrayList<Question>();
		Question question = null;
		if (medicalHistorySet.size() == 0) {
			return questionList;
		} else {
			question = new Question();
			question.setChoices(new ArrayList<String>());
			question.setDescription("您是否有以下病史");
			questionList.add(question);
			question.setType(4);
		}
		for (Disease disease : medicalHistorySet) {
			question.getChoices().add(disease.getDiseaseName().split("#")[1]);
			i++;
			if (i % 10 == 0) {
				question = new Question();
				question.setChoices(new ArrayList<String>());
				question.setDescription("您是否有以下病史");
				questionList.add(question);
			}
		}
		if (questionList.get(questionList.size() - 1).getChoices().size() == 0) {
			questionList.remove(questionList.size() - 1);
		}
		return questionList;
	}

}
