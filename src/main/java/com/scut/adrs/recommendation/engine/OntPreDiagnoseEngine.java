package com.scut.adrs.recommendation.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.diagnose.PreDiaKnowledgeEngine;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.util.ontDaoUtils;

@Service
public class OntPreDiagnoseEngine implements PreDiaKnowledgeEngine{

	static String NS=ontDaoUtils.getNS();
	@Autowired
	OntParserDao ontParserDao;

	public OntParserDao getOntParserDao() {
		return ontParserDao;
	}

	public void setOntParserDao(OntParserDao ontParserDao) {
		this.ontParserDao = ontParserDao;
	}
	/**
	 * 根据症状去获取疾病的算法
	 */
	/**
	 * 根据症状获取该症状相关的疾病
	 * @param symptom
	 * @return 疾病集合
	 * @throws UnExistURIException
	 */
	@Override
	public Set<Disease> getRelativeDiseaseBySymptom(Symptom symptom) throws UnExistURIException {
		if(symptom==null) return null;
    	List<Restriction> reList =ontParserDao.parseRestriction(symptom.getSymptomName());
    	Set<String> interDiseaseName=new HashSet<String>();
    	Set<Disease> interDisease=new HashSet<Disease>();
    	for(Restriction re:reList){
    		if(re.isAllValuesFromRestriction()){
    			 Resource resource=re.asAllValuesFromRestriction().getAllValuesFrom();
    			 if(resource.as(OntClass.class).hasSuperClass(ontParserDao.getModel().getOntClass(ontParserDao.NS+"疾病及综合症"))){
    				 interDiseaseName.add(re.asAllValuesFromRestriction().getAllValuesFrom().getURI());	 
    			 }
    			
    		}
    		if(re.isSomeValuesFromRestriction()){
    			Resource resource=re.asSomeValuesFromRestriction().getSomeValuesFrom();
    			if(resource.as(OntClass.class).hasSuperClass(ontParserDao.getModel().getOntClass(ontParserDao.NS+"疾病及综合症"))){
    				interDiseaseName.add(re.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
   			 	}
    		}
    	}
    	for(String rdf:interDiseaseName){
    		interDisease.add(new Disease(rdf));
    	}
    	return interDisease;

	}
	/**
	 * 根据疾病获取相关的症状
	 * @param disease
	 * @return 症状集
	 */
	@Override
	public Set<Symptom> getRelativeSymptomByDisease(Disease disease) {
		String property="是症状";
    	//找到对应的所有约束
    	Set<Restriction> reSet=this.ontParserDao.getRestriction(property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互症状的集合
    	Set<Symptom> interSymptom=new HashSet<Symptom>();
    	//System.out.println("大小"+reSet.size());
    	 for (Restriction re : reSet) {
    		 ontClassSet.addAll(ontParserDao.getSubClass(re));
         }
    	 //开始构造交互症状
    	String symptomURI=NS+"症状";
    	for(OntClass ontclass:ontClassSet){

    		if(ontclass.hasSuperClass(ontParserDao.getModel().getOntClass(symptomURI))){
    			interSymptom.add(new Symptom(ontclass.getURI()));
    		}
    	}
    	return interSymptom;
	}
	/**
	 * 根据疾病获取相关的病因
	 * @param disease
	 * @return 病因集
	 */
	@Override
	public Set<Pathogeny> getRelativePathogenyByDisease(Disease disease) {
		//定义算法属性
    	String property="诱发";
    	//找到对应的所有约束
    	Set<Restriction> reSet=this.ontParserDao.getRestriction( property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互病因的集合
    	Set<Pathogeny> interPathogeny=new HashSet<Pathogeny>();
    	 for (Restriction re : reSet) {
    		 ontClassSet.addAll(ontParserDao.getSubClass(re));
         }
    	 //开始构造交互病因
    	 String pathgenyURI=NS+"病因";
    	for(OntClass ontclass:ontClassSet){
    		if(ontclass.hasSuperClass(ontParserDao.getModel().getOntClass(pathgenyURI))){
    			interPathogeny.add(new Pathogeny(ontclass.getURI()));
    		}
    	}
    	return interPathogeny;
	}
	/**
	 * 根据症状获取相关的疾病（引发，导致）
	 * @param disease
	 * @return 疾病集
	 */
	@Override
	public Set<Disease> getRelativeDiseaseByDisease(Disease disease) {
		//引发
    	String property="导致";
    	//找到对应的所有约束
    	Set<Restriction> reSet=ontParserDao.getRestriction(property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互症状的集合
    	Set<Disease> interDisease=new HashSet<Disease>();
    	for (Restriction re : reSet) {
    		ontClassSet.addAll(ontParserDao.getSubClass(re));
            
        }
    	//开始构造交互疾病
    	String diseaseURI=NS+"疾病及综合症";
    	for(OntClass ontclass:ontClassSet){
    		if(ontclass.hasSuperClass(ontParserDao.getModel().getOntClass(diseaseURI))){
    			interDisease.add(new Disease(ontclass.getURI()));
    		}
    	}
    	return interDisease;
	}
	/**
	 * 根据疾病获取相关的体征
	 * @param disease
	 * @return 体征集
	 */
	@Override
	public Set<BodySigns> getRelativeBodySignsByDisease(Disease disease) {
		String property="是体征";
    	//找到对应的所有约束
    	Set<Restriction> reSet=this.ontParserDao.getRestriction(property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互症状的集合
    	Set<BodySigns> interBodySigns=new HashSet<BodySigns>();
    	 for (Restriction re : reSet) {
    		 ontClassSet.addAll(ontParserDao.getSubClass(re));
         }
    	 //开始构造交互体征
    	String bodySignsURI=NS+"体征";
    	for(OntClass ontclass:ontClassSet){

    		if(ontclass.hasSuperClass(ontParserDao.getModel().getOntClass(bodySignsURI))){
    			interBodySigns.add(new BodySigns(ontclass.getURI()));
    		}
    	}

		return interBodySigns;
	}

}
