package com.scut.adrs.recommendation.diagnose.imp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.InterQuestion;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.diagnose.PreDiaKnowledgeEngine;
import com.scut.adrs.recommendation.diagnose.PreDiagnosis;
import com.scut.adrs.recommendation.exception.UnExistURIException;
/**
 * 该类为预诊断服务类，编写预诊断业务流程，主要作用为根据病人初始信息，完善整个患病模型，表现为与用户做交互！
 * @author FAN
 *
 */
@Service
public class PreDiagnosisImp implements PreDiagnosis {
	/*
	 * 预诊断算法引擎接口，该引擎可以根据不同本体库替换，只需实现该接口可保证整个预诊断业务正常进行
	 */
	@Autowired
	private PreDiaKnowledgeEngine knowledgeEngine;
	
	
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
			//System.out.println("疾病为空吗？"+disease.getDiseaseName());
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
	
	/**
	 * 该方法是预诊断的第二个功能，交互后确认的信息和病人对象，填充整个患病模型
	 * @return 完整的病人模型
	 */
	@Override
	public Patient prediagnosis(Patient patient, InterQuestion interQuestion) {
		patient.getHasSymptoms().addAll(interQuestion.getHasSymptoms());
		patient.getHasBodySigns().addAll(interQuestion.getHasBodySigns());
		patient.getHasPathogeny().addAll(interQuestion.getHasPathogeny());
		patient.getHasMedicalHistory().addAll(interQuestion.getHasMedicalHistory());
		patient.setPreDiagnosis(true);
		return patient;
	}
	

}
