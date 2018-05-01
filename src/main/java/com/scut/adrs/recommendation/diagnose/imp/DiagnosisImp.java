package com.scut.adrs.recommendation.diagnose.imp;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.recommendation.diagnose.Diagnose;
import com.scut.adrs.recommendation.diagnose.DiagnoseKnowledgeEngine;
import com.scut.adrs.recommendation.exception.DiagnoseException;
@Component
public class DiagnosisImp implements Diagnose{
	//consinEngine
	@Autowired
	@Qualifier("consinEngine")
	DiagnoseKnowledgeEngine knowledgeEngine;
	@Override
	public Patient diagnose(Patient patient) {
		
		if(!patient.isPreDiagnosis()){
			throw new DiagnoseException("please prediagnose the patient before you diagnose a patient !");
		}
		patient =knowledgeEngine.defineDiseaseIndex(patient);
		sortAndLimited(patient.getDiseaseAndIndex(), 5); 
		return patient;
	}
	/**
	 * 把病人的诊断结果按指数排序，并指列出有限个
	 * @return
	 */
	private Map<Disease,Float> sortAndLimited(Map<Disease,Float> diseaseAndIndex,int Limited){
		//List<Entry<Disease,Float>> list=new ArrayList<>();
		List<Map.Entry<Disease, Float>> list =new LinkedList<Map.Entry<Disease, Float>>( diseaseAndIndex.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<Disease, Float>>()  
		        {  
					@Override
					public int compare(Entry<Disease, Float> arg0,Entry<Disease, Float> arg1) {
						return arg1.getValue().compareTo(arg0.getValue());
					}  
		        } );
		
		for(Map.Entry<Disease, Float> entry:list){
			//entry.setValue(10000F);
			Limited--;
			if(Limited>=0) continue;
			//System.out.println("排序后："+entry.getKey().getDiseaseName()+entry.getValue());
			diseaseAndIndex.remove(entry.getKey());
			
		}
		return diseaseAndIndex;
		
	}
}
