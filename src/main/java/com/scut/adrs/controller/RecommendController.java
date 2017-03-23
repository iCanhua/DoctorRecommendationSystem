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
import com.scut.adrs.analyticallayer.service.DoctorService;
import com.scut.adrs.analyticallayer.service.PatientService;
import com.scut.adrs.analyticallayer.service.SearchService;
import com.scut.adrs.analyticallayer.service.SymptomService;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Department;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.nlcomprehension.service.DescriptionComprehension;
import com.scut.adrs.nlcomprehension.service.InterConceptQuestion;
import com.scut.adrs.recommendation.service.RecommendationProxy;
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
	PatientService patientService;

	@Autowired
	RecommendationProxy proxy;

	@Autowired
	SearchService searchService;

	@Autowired
	DescriptionComprehension descriptionComprehension;

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

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
	 * @return
	 * @Description 有问必答搜索页面
	 */
	@RequestMapping("/search")
	public String search() {
		return "search";
	}

	/**
	 * 
	 * @return 异步获取症状
	 */
	@RequestMapping("/getSymptoms")
	@ResponseBody
	public List<Symptom> getSymptoms(String position) {
		List<Symptom> symptoms = symptomService.getSymptomList("按" + position + "分");
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
	public String questionListJsp(String symptoms, HttpSession session, Model model) {
		if (symptoms == null || symptoms.equals("")) {
			return "redirect:/symptom";
		}
		String[] symptomsArray = symptoms.split(",");
		Set<Symptom> symptomSet = new HashSet<Symptom>();
		String NS = ontDaoUtils.getNS();
		for (String symptom : symptomsArray) {
			symptomSet.add(new Symptom(NS + symptom));
		}
		Patient patient = new Patient();
		patient.setHasSymptoms(symptomSet);
		List<Question> questionList = patientService.getQuestionByPatient(patient);
		model.addAttribute("questions", questionList);
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
	public String recommendJsp(String symptoms, String bodySigns, String pathogenys, String diseases,
			HttpSession session, Model model) {
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
		Map<Disease, Float> diseaseAndIndex = patient.getDiseaseAndIndex();
		List<Map.Entry<Disease, Float>> diseaseList = new ArrayList<Map.Entry<Disease, Float>>(
				diseaseAndIndex.entrySet());
		SortUtil.disaseSort(diseaseList);
		Map<Doctor, Float> result = proxy.doctorMatch(patient);
		List<Map.Entry<Doctor, Float>> doctorList = new ArrayList<Map.Entry<Doctor, Float>>(result.entrySet());
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

	/**
	 * 
	 * @param uri
	 *            (医生的uri)
	 * @return 医生详细信息
	 */
	@RequestMapping("/getDoctorDetails")
	@ResponseBody
	public Doctor getDoctorDetails(String name) {
		Doctor doctor = doctorService.getDoctorData(name);
		return doctor;
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @Description 获取科室及其子科室
	 */
	@RequestMapping("/getRooms")
	@ResponseBody
	public List<Department> getRooms(String position) {
		return symptomService.getRooms();
	}

	/**
	 * 
	 * @param description
	 * @return
	 * @Description 分词，返回模糊匹配的词条
	 */
	@RequestMapping("/parse")
	@ResponseBody
	public InterConceptQuestion parse(String description, HttpSession session) {
		InterConceptQuestion interConceptQuestion = descriptionComprehension.comprehend(description);
		Patient patient = new Patient();
		patient = searchService.buildPatient(interConceptQuestion, patient);
		session.setAttribute("accuratePatient", patient);
		return interConceptQuestion;
	}

	/**
	 * 
	 * @param symptoms
	 * @param bodySigns
	 * @param pathogeny
	 * @param medicalHistory
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 * @Description 根据用户提交的分词选项返回问题
	 */
	@RequestMapping("/searchSubmit")
	public String searchSubmit(String[] hasSymptoms, String[] hasBodySigns, String[] hasPathogeny,
			String[] hasMedicalHistory, Model model, HttpSession session) throws Exception {
		Patient patient = (Patient) session.getAttribute("accuratePatient");
		patient = searchService.buildPatient(hasSymptoms, hasBodySigns, hasPathogeny, hasMedicalHistory, patient);
		List<Question> questionList = patientService.getQuestionByPatient(patient);
		if (questionList.size() == 0) {
			return "error";
		}
		model.addAttribute("questions", questionList);
		session.setAttribute("patient", patient);
		session.removeAttribute("accuratePatient");
		return "question";
	}

}
