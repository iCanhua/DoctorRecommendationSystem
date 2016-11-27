package com.scut.adrs.recommendation;

import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.exception.DiagnoseException;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.Diagnose;
import com.scut.adrs.recommendation.service.DoctorMatch;
import com.scut.adrs.recommendation.service.PreDiagnosis;
import com.scut.adrs.util.ontDaoUtils;

@Service
public class RecommendationProxy implements PreDiagnosis, Diagnose, DoctorMatch {
	@Autowired
	private PreDiagnosis preDiagnose;
	@Autowired
	private Diagnose diagnose;
	@Autowired
	private DoctorMatch doctorMatch;

	Map<Doctor, Float> doctorAndIndex;

	public Diagnose getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(Diagnose diagnose) {
		this.diagnose = diagnose;
	}

	public DoctorMatch getDoctorMatch() {
		return doctorMatch;
	}

	public void setDoctorMatch(DoctorMatch doctorMatch) {
		this.doctorMatch = doctorMatch;
	}

	public PreDiagnosis getPreDiagnose() {
		return preDiagnose;
	}

	public void setPreDiagnose(PreDiagnosis preDiagnose) {
		this.preDiagnose = preDiagnose;
	}

	@Override
	public InterQuestion prediagnosis(Patient patient)
			throws UnExistURIException {
		// this.preDiagnose.prediagnosis(patient);
		if (patient == null) {
			return null;
		}
		return this.preDiagnose.prediagnosis(patient);
	}

	@Override
	public Patient prediagnosis(Patient patient, InterQuestion question) {
		Patient pt = preDiagnose.prediagnosis(patient, question);
		// pt.setPreDiagnosis(true);
		return pt;
	}

	public static void main(String[] args) throws UnExistURIException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring*.xml");
		// PreDiagnosis pd=ctx.getBean(PreDiagnosisImp.class);
		Patient patient = new Patient();
		patient.addSymptom(ontDaoUtils.getNS() + "黑蒙");
		patient.addSymptom(ontDaoUtils.getNS() + "癫痫");
		patient.addSymptom(ontDaoUtils.getNS() + "上腹部胀满");
		patient.addSymptom(ontDaoUtils.getNS() + "胸闷");
		patient.addSymptom(ontDaoUtils.getNS() + "失语");
		patient.addSymptom(ontDaoUtils.getNS() + "二尖瓣脱垂");
		// InterQuestion question=pd.prediagnosis(patient);
		// pd.prediagnosis(patient, question);
		// Diagnose diagnose=ctx.getBean(DiagnosisImp.class);
		// diagnose.diagnose(patient);
		RecommendationProxy proxy = ctx.getBean(RecommendationProxy.class);
		InterQuestion question = proxy.prediagnosis(patient);
		proxy.diagnose(patient);
		proxy.doctorMatch(patient);
		OntParserDao ontDao = ctx.getBean(OntParserDao.class);

		for (BodySigns bs : question.getHasBodySigns()) {
			System.out.println("交互体征：" + bs.getBodySignName());
		}
		for (Symptom sy : question.getHasSymptoms()) {
			System.out.println("交互症状：" + sy.getSymptomName());
		}
		for (Pathogeny py : question.getHasPathogeny()) {
			System.out.println("病因：" + py.getPathogenyName());
		}
		for (Disease ds : question.getHasMedicalHistory()) {
			System.out.println("病史：" + ds.getDiseaseName());
		}
		Set<Disease> a = patient.getDiseaseAndIndex().keySet();
		for (Disease py : a) {
			System.out.println("患病" + py.getDiseaseName());
			System.out.println("概率" + patient.getDiseaseAndIndex().get(py));
		}

		Map<Doctor, Float> map = proxy.doctorMatch(patient);
		Set<Doctor> b = map.keySet();
		Model model = ontDao.getModel();
		for (Doctor dt : b) {

			Individual ind = model.getResource(dt.getName()).as(
					Individual.class);
			System.out.println("医生:" + ind.getURI());
			System.out.println("概率:" + map.get(dt));
		}

	}

	@Override
	public Patient diagnose(Patient patient) {
//		for(Symptom sy:patient.getHasSymptoms()){
//			System.out.println("总症状："+sy.getSymptomName());
//		}
//		for(Pathogeny py:patient.getHasPathogeny()){
//			System.out.println("病因："+py.getPathogenyName());
//		}
//		for(BodySigns bs:patient.getHasBodySigns()){
//			System.out.println("体征"+bs.getBodySignName());
//		}
//		for(Disease d:patient.getHasMedicalHistory()){
//			System.out.println("病史"+d.getDiseaseName());
//		}
		if (!patient.isPreDiagnosis()) {
			throw new DiagnoseException(
					"please prediagnose the patient before you diagnose a patient !");
		}
		return diagnose.diagnose(patient);
	}

	@Override
	public Map<Doctor, Float> doctorMatch(Patient patient) {
		if (patient == null) {
			return null;
		}
		if (!patient.isPreDiagnosis()) {
			throw new DiagnoseException(
					"please prediagnose the patient before you match a doctor !");
		}
		return doctorMatch.doctorMatch(patient);
	}

}
