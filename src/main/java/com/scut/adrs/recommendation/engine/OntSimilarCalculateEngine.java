package com.scut.adrs.recommendation.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.match.DocMatchKnowledgeEngine;
/**
 * 该类为病人匹配医生，该引擎算法实现比较简陋！仅匹配擅长和尤其擅长概念
 * @author FAN
 *
 */
@Service
public class OntSimilarCalculateEngine implements DocMatchKnowledgeEngine {
	@Autowired
	OntParserDao ontParserDao;

	public OntParserDao getOntParserDao() {
		return ontParserDao;
	}

	public void setOntParserDao(OntParserDao ontParserDao) {
		this.ontParserDao = ontParserDao;
	}

	@Override
	public Map<Doctor, Float> doctorMatch(Patient patient) {
		Set<Restriction> reSetAt=ontParserDao.getRestriction("擅长");
		Set<Restriction> reSetGoodAt=ontParserDao.getRestriction("尤其擅长");
		
		Map<Doctor, Float> doctorMap = new HashMap<Doctor, Float>();
		Map<Disease,Float> DiseaseMap=patient.getDiseaseAndIndex();
		//为每个疾病丰富其父类
		Map<Disease,Float> DiseaseMap_Temp=new HashMap<Disease, Float>();
		for(Disease ds:DiseaseMap.keySet()){
			//寻找父类
			Set<OntClass> ralativSet=ontParserDao.getSuperClass(false, ds.getDiseaseName());
			Iterator<OntClass> itor=ralativSet.iterator();
			while (itor.hasNext()) {
				OntClass temp = itor.next();

				if(!temp.hasSuperClass(ontParserDao.getModel().getOntClass(ontParserDao.NS+"疾病及综合症"))){
					itor.remove();
				}
			}
			for(OntClass ont:ralativSet){
				DiseaseMap_Temp.put(new Disease(ont.getURI()), DiseaseMap.get(ds));
			}
		}
		DiseaseMap.putAll(DiseaseMap_Temp);
		
		for(Restriction r:reSetAt){
			if (r.isSomeValuesFromRestriction()) {
				Disease matchDisease=new Disease(r.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
				if(DiseaseMap.keySet().contains(matchDisease)){
					String docUri=r.asSomeValuesFromRestriction().listInstances().next().getURI();
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=index+doctorMap.get(doctor);
							doctorMap.put(doctor, index);
						}else{
							doctorMap.put(doctor, DiseaseMap.get(matchDisease));
						}
						
					}
				}
			}
			if (r.isAllValuesFromRestriction()) {
				Disease matchDisease=new Disease(r.asAllValuesFromRestriction().getAllValuesFrom().getURI());
				if(DiseaseMap.keySet().contains(matchDisease)){
					String docUri=r.asAllValuesFromRestriction().listInstances().next().getURI();
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=index+doctorMap.get(doctor);
							doctorMap.put(doctor, index);
						}else{
							doctorMap.put(doctor, DiseaseMap.get(matchDisease));
						}
					}
				}
			}
		}
		for(Restriction r:reSetGoodAt){
			if (r.isSomeValuesFromRestriction()) {
				Disease matchDisease=new Disease(r.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
				if(DiseaseMap.keySet().contains(matchDisease)){
					String docUri=r.asSomeValuesFromRestriction().listInstances().next().getURI();
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=(index+doctorMap.get(doctor))*1.5F;
							doctorMap.put(doctor, index);
						}else{
							doctorMap.put(doctor, DiseaseMap.get(matchDisease));
						}
					}
				}
			}
			if (r.isAllValuesFromRestriction()) {
				Disease matchDisease=new Disease(r.asAllValuesFromRestriction().getAllValuesFrom().getURI());
				if(DiseaseMap.keySet().contains(matchDisease)){
					String docUri=r.asAllValuesFromRestriction().listInstances().next().getURI();
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=(index+doctorMap.get(doctor))*1.5F;
							doctorMap.put(doctor, index);
						}else{
							doctorMap.put(doctor, DiseaseMap.get(matchDisease));
						}
					}
				}
			}
		}
		

		return doctorMap;
	}
	
	

}
