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
import com.scut.adrs.recommendation.service.DocMatchKnowledgeEngine;

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
				if(!this.isSuperClass(ontParserDao.NS+"疾病及综合症", temp)){
					itor.remove();
				}
			}
			for(OntClass ont:ralativSet){
				//把相关父类以同样的指数加入该疾病集
				//System.out.println(ds.getDiseaseName()+"的父类"+ont.getLocalName());
				DiseaseMap_Temp.put(new Disease(ont.getURI()), DiseaseMap.get(ds));
			}
		}
		DiseaseMap.putAll(DiseaseMap_Temp);
		
		for(Restriction r:reSetAt){
			if (r.isSomeValuesFromRestriction()) {
				Disease matchDisease=new Disease(r.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
				if(DiseaseMap.keySet().contains(matchDisease)){
					String docUri=r.asSomeValuesFromRestriction().listInstances().next().getURI();
					//System.out.println("找到医生："+docUri+"：这个医生擅长"+r.asSomeValuesFromRestriction().getSomeValuesFrom().getLocalName());
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=index+doctorMap.get(doctor);
							doctorMap.put(doctor, index);
							//System.out.println(doctor.getName()+"这个医生升了："+index);
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
					//System.out.println("找到医生："+docUri);
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=index+doctorMap.get(doctor);
							doctorMap.put(doctor, index);
							//System.out.println(doctor.getName()+"这个医生升了："+index);
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
					//System.out.println("找到尤其擅长的医生："+docUri+"：这个医生擅长"+r.asSomeValuesFromRestriction().getSomeValuesFrom().getLocalName());
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=(index+doctorMap.get(doctor))*1.5F;
							doctorMap.put(doctor, index);
							//System.out.println(doctor.getName()+"这个医生升了："+index);
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
					//System.out.println("找到尤其擅长的医生："+docUri);
					if("".equals(docUri)||docUri!=null){
						Doctor doctor=new Doctor(docUri);
						if(doctorMap.containsKey(doctor)){
							Float index=DiseaseMap.get(matchDisease);
							index=(index+doctorMap.get(doctor))*1.5F;
							doctorMap.put(doctor, index);
							//System.out.println(doctor.getName()+"这个医生升了："+index);
						}else{
							doctorMap.put(doctor, DiseaseMap.get(matchDisease));
						}
					}
				}
			}
		}
		
		
		
		
		
		// System.out.println("找医生");
//		Doctor doctor1 = new Doctor(OntParserDao.NS + "刘烈");
//		Doctor doctor2 = new Doctor(OntParserDao.NS + "张曹进");
//		Doctor doctor3 = new Doctor(OntParserDao.NS + "林展翼");
//		Doctor doctor4 = new Doctor(OntParserDao.NS + "吴书林");
//		doctorMap.put(doctor4, 23.02F);
//		doctorMap.put(doctor2, 53.02F);
//		doctorMap.put(doctor3, 2.02F);
//		doctorMap.put(doctor1, 13.02F);
		return doctorMap;
	}
	/**
	 * 抽取共同部分代码，判断某个本体是否为superURI的之类
	 * @param superRdf
	 * @param ontClass
	 * @return
	 */
	private boolean isSuperClass(String superURI,OntClass ontClass){
		boolean finded=false;
		ExtendedIterator<OntClass> iterator=ontClass.listSuperClasses(false);
		while(iterator.hasNext()){
			OntClass superClass=(OntClass) iterator.next();
			if(superURI.equals(superClass.getURI())){
				finded=true;
			}
		}
		return finded;
	}
	public static void main(String[] args) {
		
	}

}
