package com.scut.adrs.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scut.adrs.analyticallayer.dto.Question;
import com.scut.adrs.analyticallayer.service.SymptomService;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.InterQuestion;
import com.scut.adrs.recommendation.RecommendationProxy;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.DoctorService;
import com.scut.adrs.util.EncodingConvert;
import com.scut.adrs.util.QuestionUtil;
import com.scut.adrs.util.SortUtil;
import com.scut.adrs.util.ontDaoUtils;

@Controller
public class RecommendController {

	static Patient patient;

	@Autowired
	SymptomService symptomService;

	@Autowired
	DoctorService doctorService;

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
	public String questionListJsp(String symptoms, HttpSession session,
			Model model) {
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
		} catch (UnExistURIException e) {
			e.printStackTrace();
		}
		model.addAttribute("questions", questionList);
		model.addAttribute("symptoms", EncodingConvert.convert(symptoms));
		session.setAttribute("patient", patient);
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
	public String recommendJsp(String symptoms, String bodySigns,
			String pathogenys, String diseases, HttpSession session, Model model) {
		String NS = ontDaoUtils.getNS();
		patient = (Patient) session.getAttribute("patient");
		if (patient == null) {
			model.addAttribute("flag", false);
			return "doctor";
		} else {
			model.addAttribute("flag", true);
		}
		patient.setPreDiagnosis(true);
		if (symptoms != null && !symptoms.equals("")) {
			Set<Symptom> symptomSet = new HashSet<Symptom>();
			for (String symptom : symptoms.split(",")) {
				symptomSet.add(new Symptom(NS + symptom));
			}
			patient.getHasSymptoms().addAll(symptomSet);
		}
		if (bodySigns != null && !bodySigns.equals("")) {
			Set<BodySigns> bodySignSet = new HashSet<BodySigns>();
			for (String bodySign : bodySigns.split(",")) {
				bodySignSet.add(new BodySigns(NS + bodySign));
			}
			patient.getHasBodySigns().addAll(bodySignSet);
		}
		if (pathogenys != null && !pathogenys.equals("")) {
			Set<Pathogeny> pathogenySet = new HashSet<Pathogeny>();
			for (String pathogeny : pathogenys.split(",")) {
				pathogenySet.add(new Pathogeny(NS + pathogeny));
			}
			patient.getHasPathogeny().addAll(pathogenySet);
		}
		if (diseases != null && !diseases.equals("")) {
			Set<Disease> diseaseSet = new HashSet<Disease>();
			for (String disease : diseases.split(",")) {
				diseaseSet.add(new Disease(NS + disease));
			}
			patient.getHasMedicalHistory().addAll(diseaseSet);
		}
		proxy.diagnose(patient);
//		for (Symptom symptom : patient.getHasSymptoms()) {
//			System.out.println(symptom.getSymptomName());
//		}
//		for (BodySigns bodySign : patient.getHasBodySigns()) {
//			System.out.println(bodySign.getBodySignName());
//		}
//		for (Disease disease : patient.getHasMedicalHistory()) {
//			System.out.println(disease.getDiseaseName());
//		}
//		for (Pathogeny pathogeny : patient.getHasPathogeny()) {
//			System.out.println(pathogeny.getPathogenyName());
//		}
		Map<Disease, Float> diseaseAndIndex = patient.getDiseaseAndIndex();
		List<Map.Entry<Disease, Float>> diseaseList = new ArrayList<Map.Entry<Disease, Float>>(
				diseaseAndIndex.entrySet());
		SortUtil.disaseSort(diseaseList);
		Map<Doctor, Float> result = proxy.doctorMatch(patient);
		List<Map.Entry<Doctor, Float>> doctorList = new ArrayList<Map.Entry<Doctor, Float>>(
				result.entrySet());
		SortUtil.doctorSort(doctorList);
		for (Map.Entry<Disease, Float> entry : diseaseList) {
			String uri = entry.getKey().getDiseaseName();
			String introduction = doctorService.getIntroduction(uri);
			entry.getKey().setIntroduction(introduction);
		}
		for (Map.Entry<Doctor, Float> entry : doctorList) {
			String uri = entry.getKey().getName();
			String introduction = doctorService.getIntroduction(uri);
			entry.getKey().setIntroduction(introduction.replace("\n", "<br/>"));
		}

		model.addAttribute("doctors", doctorList);
		model.addAttribute("sicks", diseaseList);
		session.removeAttribute("patient");
		return "doctor";
	}
}
