package com.scut.adrs.recommendation.service.imp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.InterQuestion;
import com.scut.adrs.recommendation.PreDiagnosis;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.PreDiaKnowledgeEngine;

@Service
public class PreDiagnosisImp implements PreDiagnosis {
	@Autowired
	PreDiaKnowledgeEngine knowledgeEngine;
	public PreDiaKnowledgeEngine getKnowledgeEngine() {
		return knowledgeEngine;
	}
	public void setKnowledgeEngine(PreDiaKnowledgeEngine knowledgeEngine) {
		this.knowledgeEngine = knowledgeEngine;
	}
	/**
	 * 预诊断，根据病人的症状信息去构建和病人交互的问题对象！
	 */
	@Override
	public InterQuestion prediagnosis(Patient patient) throws UnExistURIException  {
		
		InterQuestion interQuestion=new InterQuestion();
		
		//初始化可能患有的疾病
		Set<Disease> interDisease=new HashSet<Disease>();
		for (Symptom element:patient.getHasSymptoms()) {
			interDisease.addAll(knowledgeEngine.getRelativeDiseaseBySymptom(element));
		}
		//初始化可能患有的疾病,并赋予初始值。
		for (Disease disease:interDisease) {
			patient.addDisease(disease, 0.0f);
			//System.out.println("病的名字："+disease.getDiseaseName());
		}
		//构建相关症状
		Set<Symptom> interSymptoms=new HashSet<Symptom>();
		for(Disease disease:interDisease){
			interSymptoms.addAll(knowledgeEngine.getRelativeSymptomByDisease( disease));
		}
		interSymptoms.removeAll(patient.getHasSymptoms());//已经有的无需加入交互症状
		interQuestion.setHasSymptoms(interSymptoms);
		//构建相关体征
		Set<BodySigns> interBodySign=new HashSet<BodySigns>();
		for(Disease disease:interDisease){
			interBodySign.addAll(knowledgeEngine.getRelativeBodySignsByDisease(disease));
		}
		interBodySign.removeAll(patient.getHasBodySigns());
		interQuestion.setHasBodySigns(interBodySign);
		//构建相关病因
		Set<Pathogeny> interPathogeny=new HashSet<Pathogeny>();
		for (Disease disease:interDisease){
			interPathogeny.addAll(knowledgeEngine.getRelativePathogenyByDisease(disease));
		}
		interQuestion.setHasPathogeny(interPathogeny);
		
		//构造相关病史
		Set<Disease> interMedicalHistory=new HashSet<Disease>();
		for(Disease disease:interDisease){
			interMedicalHistory.addAll(knowledgeEngine.getRelativeDiseaseByDisease( disease));
		}
		interQuestion.setHasMedicalHistory(interMedicalHistory);
		return interQuestion;
	}

	@Override
	public Patient prediagnosis(Patient patient, InterQuestion interQuestion) {
		patient.getHasSymptoms().addAll(interQuestion.getHasSymptoms());
		patient.getHasBodySigns().addAll(interQuestion.getHasBodySigns());
		patient.getHasPathogeny().addAll(interQuestion.getHasPathogeny());
		patient.getHasMedicalHistory().addAll(interQuestion.getHasMedicalHistory());
		patient.setPreDiagnosis(true);
		return patient;
	}
	public static void main(String[] args) {
		
	}

}
