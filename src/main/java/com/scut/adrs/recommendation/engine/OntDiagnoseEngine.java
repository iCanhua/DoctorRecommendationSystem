package com.scut.adrs.recommendation.engine;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.DiagnoseKnowledgeEngine;

@Service
public class OntDiagnoseEngine implements DiagnoseKnowledgeEngine{
	Float s=0F,b=0F,p=0F,d=0F;
	@Autowired
	OntParserDao ontParserDao;
	public OntParserDao getOntParserDao() {
		return ontParserDao;
	}
	public void setOntParserDao(OntParserDao ontParserDao) {
		this.ontParserDao = ontParserDao;
	}
	
	@Override
	public Patient defineDiseaseIndex(Patient patient) {
		if(patient==null){
			return null;
		}
		//根据症状为疾病评分
		Set<Symptom> sympthomSet = patient.getHasSymptoms();
		for(Symptom sy:sympthomSet){
			Set<Disease> diseaseSet;
			try {
				diseaseSet=getRestritionRelativeDiseaseByOntClass(sy.getSymptomName());
			} catch (UnExistURIException e) {
				continue;
			}
			//返回值未赋值给患者，引用传递
			indexEvaluate(patient.getDiseaseAndIndex(), diseaseSet, 1.0F);
			//s++;
			//System.out.println("得分症状为"+sy.getSymptomName());
			
		}
		//根据体征为疾病评分
		Set<BodySigns> bodySignsSet=patient.getHasBodySigns();
		for(BodySigns bs:bodySignsSet){
			Set<Disease> diseaseSet;
			try {
				diseaseSet=getRestritionRelativeDiseaseByOntClass(bs.getBodySignName());
			} catch (UnExistURIException e) {
				continue;
			}
			//返回值未赋值给患者，引用传递
			indexEvaluate(patient.getDiseaseAndIndex(), diseaseSet, 1.0F);
			//b++;
			//System.out.println("得分体征："+bs.getBodySignName());
		}
		//根据病因为疾病评分
		Set<Pathogeny> pathogenySet=patient.getHasPathogeny();
		for(Pathogeny py:pathogenySet){
			Set<Disease> diseaseSet;
			try {
				diseaseSet=getRestritionRelativeDiseaseByOntClass(py.getPathogenyName());
			} catch (UnExistURIException e) {
				continue;
			}
			//返回值未赋值给患者，引用传递
			indexEvaluate(patient.getDiseaseAndIndex(), diseaseSet, 1.0F);
			//p++;
			//System.out.println("得分病因"+py.getPathogenyName());
		}
		//根据病因为疾病评分
		Set<Disease> diseaseSet=patient.getHasMedicalHistory();
		for(Disease ds:diseaseSet){
			Set<Disease> set;
			try {
				set=getRestritionRelativeDiseaseByOntClass(ds.getDiseaseName());
			} catch (UnExistURIException e) {
				continue;
			}
			//返回值未赋值给患者，引用传递
			indexEvaluate(patient.getDiseaseAndIndex(), set, 1.0F);
			//d++;
			//System.out.println("得分病史"+ds.getDiseaseName());
		}
		//System.out.println("s:"+s+",b:"+b+",p:"+p+",d:"+d+"__测试统计");
		return patient;
	}
	/**
	 * 根据评分index累加到疾病的index中
	 * @param diseaseAndIndex
	 * @param diseaseSet
	 * @param index
	 * @return
	 */
	private Map<Disease,Float> indexEvaluate(Map<Disease,Float> diseaseAndIndex,Set<Disease> diseaseSet,Float score){
		if(diseaseAndIndex==null||diseaseSet==null){
			return null;
		}
		if(diseaseSet.size()!=0){
			for(Disease disease:diseaseSet){
		
				if(diseaseAndIndex.containsKey(disease)){
					Float index=(Float)diseaseAndIndex.get(disease)+score;
					diseaseAndIndex.put(disease, index);
				}
			}
		}
		return diseaseAndIndex;
	}
	/**
	 * 根据URI获取对应的约束，并返回约束FromValue为疾病构成的集合
	 * @param URI
	 * @return
	 * @throws UnExistURIException
	 */
	private Set<Disease> getRestritionRelativeDiseaseByOntClass(String URI) throws UnExistURIException{
		List<Restriction> reList=ontParserDao.parseRestriction(URI);
		Set<Disease> diseaseSet=new HashSet<Disease>();
		for(Restriction re:reList){
    		if(re.isAllValuesFromRestriction()){
    			OntClass ontClass=re.asAllValuesFromRestriction().getAllValuesFrom().as(OntClass.class);
    			if(isSuperClass(OntParserDao.NS+"疾病及综合症", ontClass)){
    				diseaseSet.add(new Disease(ontClass.getURI()));
    			}
    		}
    		if(re.isSomeValuesFromRestriction()){
    			OntClass ontClass=re.asSomeValuesFromRestriction().getSomeValuesFrom().as(OntClass.class);
    			if(isSuperClass(OntParserDao.NS+"疾病及综合症", ontClass)){
    				diseaseSet.add(new Disease(ontClass.getURI()));
    			}
    		}
    	}
		return diseaseSet;
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


}
