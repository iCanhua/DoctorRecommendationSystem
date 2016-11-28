package com.scut.adrs.recommendation.service.imp;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.recommendation.service.DocMatchKnowledgeEngine;
import com.scut.adrs.recommendation.service.DoctorMatch;
@Service
public class DoctorMatchImp implements DoctorMatch {
	@Autowired
	DocMatchKnowledgeEngine docMatchKnowledgeEngine;
	
	public DocMatchKnowledgeEngine getDocMatchKnowledgeEngine() {
		return docMatchKnowledgeEngine;
	}

	public void setDocMatchKnowledgeEngine(
			DocMatchKnowledgeEngine docMatchKnowledgeEngine) {
		this.docMatchKnowledgeEngine = docMatchKnowledgeEngine;
	}

	@Override
	public Map<Doctor,Float> doctorMatch(Patient patient) {
		if(patient==null){return null;}
		Map<Doctor,Float> doctorAndIndex= docMatchKnowledgeEngine.doctorMatch(patient);
		doctorAndIndex=sortAndLimited(doctorAndIndex, 5);
		return doctorAndIndex;
	}
	private Map<Doctor,Float> sortAndLimited(Map<Doctor,Float> doctorAndIndex,int Limited){
		//List<Entry<Disease,Float>> list=new ArrayList<>();
		List<Map.Entry<Doctor, Float>> list =new LinkedList<Map.Entry<Doctor, Float>>( doctorAndIndex.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<Doctor, Float>>()  
		        {  
					@Override
					public int compare(Entry<Doctor, Float> arg0,Entry<Doctor, Float> arg1) {
						return arg1.getValue().compareTo(arg0.getValue());
					}  
		        } );
		for(Entry<Doctor, Float> entry:list){
			//entry.setValue(10000F);
			Limited--;
			if(Limited>=0) continue;
			//System.out.println("排序后："+entry.getKey().getDiseaseName()+entry.getValue());
			doctorAndIndex.remove(entry.getKey());
			
		}
		return doctorAndIndex;
	}

}
