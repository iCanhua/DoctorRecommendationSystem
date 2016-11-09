package com.scut.adrs.recommendation.service;

import java.util.Iterator;

import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.InterQuestion;
import com.scut.adrs.recommendation.PreDiagnosis;
import com.scut.adrs.recommendation.exception.UnExistRdfException;
import com.scut.adrs.recommendation.imp.PreDiagnosisImp;
import com.scut.adrs.util.ontDaoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RecommendationProxy implements PreDiagnosis{
	@Autowired
	private PreDiagnosis preDiagnose;
	
	public PreDiagnosis getPreDiagnose() {
		return preDiagnose;
	}

	public void setPreDiagnose(PreDiagnosis preDiagnose) {
		this.preDiagnose = preDiagnose;
	}

	@Override
	public InterQuestion prediagnosis(Patient patient) throws UnExistRdfException {
		//this.preDiagnose.prediagnosis(patient);
		
		return this.preDiagnose.prediagnosis(patient);
	}

	@Override
	public Patient prediagnosis(Patient patient, InterQuestion question) {
		
		Patient pt=this.prediagnosis(patient, question);
		pt.setPreDiagnosis(true);
		return pt;
	}
	
	public static void main(String[] args) throws UnExistRdfException {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("spring/spring*.xml");
		PreDiagnosis pd=ctx.getBean(PreDiagnosisImp.class);
		Patient patient=new Patient();
		patient.addSymptom(ontDaoUtils.getNS()+"发热");
		InterQuestion question=pd.prediagnosis(patient);
		for(Disease py:question.getHasMedicalHistory()){
			System.out.println("病史："+py.getDiseaseName());
		}
		
	}
	

}
