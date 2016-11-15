package com.scut.adrs.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.analyticallayer.service.SymptomService;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.InterQuestion;
import com.scut.adrs.recommendation.RecommendationProxy;
import com.scut.adrs.recommendation.exception.UnExistRdfException;
import com.scut.adrs.recommendation.service.TestService;
import com.scut.adrs.util.EncodingConvert;
import com.scut.adrs.util.QuestionUtil;
import com.scut.adrs.util.ontDaoUtils;

@Controller
public class RecommendController {

	@Autowired
	SymptomService symptomService;

	@Autowired
	TestService testService;

	@Autowired
	RecommendationProxy proxy;

	/**
	 * 
	 * @return 症状选择页面
	 */
	@RequestMapping("/symptom")
	public String symptom() {
		return "symptom";
	}

	/**
	 * 
	 * @return 异步获取症状
	 */
	@RequestMapping("/getSymptoms")
	@ResponseBody
	public List<String> getSymptoms(String position) {
		position = EncodingConvert.convert(position);
		List<String> symptoms = symptomService.getSymptomList("按" + position
				+ "分");
		return symptoms;
	}

	/**
	 * 
	 * @param symptoms
	 *            （患者选择的症状）
	 * @param model
	 *            （传递问题参数）
	 * @return
	 */
	@RequestMapping("/questionListJsp")
	public String questionListJsp(String symptoms, Model model) {
		String[] symptomsArray = EncodingConvert.convert(symptoms).split(",");
		Set<Symptom> symptomSet = new HashSet<Symptom>();
		String NS = ontDaoUtils.getNS();
		for (String symptom : symptomsArray) {
			symptomSet.add(new Symptom(NS + symptom));
		}
		Patient patient = new Patient();
		patient.setHasSymptoms(symptomSet);
		InterQuestion interQuestion = null;
		List<Question> questionList = new ArrayList<Question>();
		try {
			interQuestion = proxy.prediagnosis(patient);
			questionList.addAll(QuestionUtil.getSymptomQuestion(interQuestion
					.getHasSymptoms()));
			questionList.addAll(QuestionUtil.getBodySigns(interQuestion
					.getHasBodySigns()));
			questionList.addAll(QuestionUtil.getPathogenyQuestion(interQuestion
					.getHasPathogeny()));
			questionList.addAll(QuestionUtil
					.getMedicalHistoryQuestion(interQuestion
							.getHasMedicalHistory()));
		} catch (UnExistRdfException e) {
			e.printStackTrace();
		}
		model.addAttribute("questions", questionList);
		model.addAttribute("symptoms", EncodingConvert.convert(symptoms));
		return "question";
	}

	/**
	 * 
	 * @param symptoms
	 *            （传递过来的symptoms字符串，用逗号分隔）
	 * @param model
	 *            （推荐医生列表保存在model中）
	 * @return 推荐医生列表界面
	 */
	@RequestMapping("/recommendJsp")
	public String recommendJsp(String symptoms, Model model) {
		List<Doctor> list = testService.getDoctor();
		model.addAttribute("doctors", list);
		return "doctor";
	}

}
