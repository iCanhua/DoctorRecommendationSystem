package com.scut.adrs.recommendation.dao;

import org.apache.jena.ontology.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;

import com.scut.adrs.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by FAN on 2016/10/21.
 */
@Repository
public class OntParserDao {
    static String NS=ontDaoUtils.getNS();
    @Autowired
    OntModel model=ontDaoUtils.getModel();
    

    public OntModel getModel() {
		return model;
	}

	public void setModel(OntModel model) {
		this.model = model;
	}

	public static void main(String[] args) {
        OntModel model= ontDaoUtils.getModel();
        //classSum(model);
        //parseRestriction(model,"全身不适");
        //getRestriction(model,model.getOntProperty(ontDaoUtils.getNS()+"擅长"),model.getResource(ontDaoUtils.getNS()+"心肌病"));
    }

    //打印本体
    public static void classSum(OntModel model){
        int i=0;
        System.out.println("本体模型类大小："+model.listClasses().toSet().size());
        Iterator it=model.listClasses();
        while (it.hasNext()){
            OntClass ontClass=(OntClass) it.next();
            if(!ontClass.isRestriction()){
                i++;
                System.out.println(i+ontClass.toString());
            }else {
                Restriction re=ontClass.asRestriction();
                re.getRDFType();
                if(re.isSomeValuesFromRestriction()){
                    String aa=re.asSomeValuesFromRestriction().getSomeValuesFrom().toString();
                    System.out.println("约束："+re.getOnProperty()+" SOME "+aa);
                }
                if(re.isAllValuesFromRestriction()){
                    String bb=re.asAllValuesFromRestriction().getAllValuesFrom().toString();
                    System.out.println("约束："+re.getOnProperty()+" ONLY "+bb);
                }
            }
        }
        System.out.println(i);
    }

    /**
     * 出现一个问题，约束出现多次！打印多次，从中发现一个问题，
     * 约束是可以多次出现原因是它把三元组关系都列了出来，
     * 也就是我可以通过这样的方式去得到所有该疾病的相关症状！
     * 
     * 该方法用于把所有与该本体的匿名父类（约束类）解析出来，也就是把所有和该本体有关的约束都返回！
     * @param model 本体模型
     * @param rdfStr 被约束类
     */
    public static List<Restriction> parseRestriction(OntModel model,String rdfStr){
    	List<Restriction> reSet=new ArrayList<Restriction>();
        OntClass ontClass=model.getOntClass(rdfStr);
        System.out.println("被约束类："+ontClass.toString());
        Iterator it =ontClass.listSuperClasses(true);
        while (it.hasNext()){
            OntClass sub=(OntClass)it.next();
            if(!sub.isRestriction()){
                System.out.println("命名类："+sub.getURI());
            }else {
                Restriction re=sub.asRestriction();
                if(re.isSomeValuesFromRestriction()){
                	reSet.add(re);
                    String aa=re.asSomeValuesFromRestriction().getSomeValuesFrom().toString();
                    System.out.println("约束："+re.asSomeValuesFromRestriction().getSubClass().toString()+re.getOnProperty()+" SOME "+aa);
                    
                }
                if(re.isAllValuesFromRestriction()){
                	reSet.add(re);
                    String bb=re.asAllValuesFromRestriction().getAllValuesFrom().toString();
                    System.out.println("约束："+re.getOnProperty()+" ONLY "+bb);
                    
                }
            }
        }
        return reSet;
    }

    /**
     * 构造约束
     * 通过指定属性和值来获得约束集合；
     */
    public static Set<Restriction> getRestriction(OntModel ontModel, String property, String values) {
		Set<Restriction> restrictionSet = new HashSet<Restriction>();
		OntProperty p = ontModel.getOntProperty(NS + property);
		Iterator<Restriction> i = p.listReferringRestrictions();
		while (i.hasNext()) {
			Restriction r = i.next();
			if (r.isSomeValuesFromRestriction()) {
				if (r.asSomeValuesFromRestriction().getSomeValuesFrom().equals(values)) {
					restrictionSet.add(r);
				}
			}
			if (r.isAllValuesFromRestriction()) {
				if (r.asAllValuesFromRestriction().getAllValuesFrom().equals(values)) {
					restrictionSet.add(r);
				}
			}
		}
		return restrictionSet;
	}
    
    //把相关约束的症状提取出来，放到集合中
    public Set<Symptom> getRelativeSymptom(Symptom symptom){
    	
    	List<Restriction> reList =parseRestriction(model,symptom.getSymptomName());
    	Set<Symptom> interSymptomSet=new HashSet<Symptom>();
    	
    	for (Restriction re:reList) {
    		//不同的症状才加入SET
    		if(!re.getSubClass().getURI().equals(symptom.getSymptomName())){
    	    		 Symptom a=new Symptom(re.asSomeValuesFromRestriction().getSubClass().getURI());
    	              interSymptomSet.add(a);
    		}
		}
    	return interSymptomSet;
    }
    
    public Set<Disease> getRelativeDiseaseBySymptom(Symptom symptom){
    	if(symptom==null) return null;
    	List<Restriction> reList =parseRestriction(model,symptom.getSymptomName());
    	Set<String> interDiseaseName=new HashSet<String>();
    	Set<Disease> interDisease=new HashSet<Disease>();
    	for(Restriction re:reList){
    		if(re.isAllValuesFromRestriction()){
    			interDiseaseName.add(re.asAllValuesFromRestriction().getAllValuesFrom().getURI());
    		}
    		if(re.isSomeValuesFromRestriction()){
    			interDiseaseName.add(re.asSomeValuesFromRestriction().getSomeValuesFrom().getURI());
    		}
    	}
    	for(String rdf:interDiseaseName){
    		interDisease.add(new Disease(rdf));
    	}
    	return interDisease;
    	
    }
    
    public Set<Pathogeny> getRelativePathogenyByDisease(String property,Disease disease){
    	//找到对应的所有约束
    	Set<Restriction> reSet=getRestriction(model, property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互病因的集合
    	Set<Pathogeny> interPathogeny=new HashSet<Pathogeny>();
    	 for (Restriction re : reSet) {
             for (Iterator<OntClass> i = re.listSubClasses(true); i.hasNext();) {
                 OntClass ontClass = i.next();
                 if (!ontClassSet.contains(ontClass))
                     System.out.println(ontClass.getLocalName());
                 ontClassSet.add(ontClass);
             }
         }
    	 //开始构造交互病因
    	for(OntClass ontclass:ontClassSet){
    		interPathogeny.add(new Pathogeny(ontclass.getURI()));
    	}
    	//这里由问题有待解决
    	return interPathogeny;
    }
    
    public Set<Symptom> getRelativeSymptomByDisease(String property,Disease disease){
    	//找到对应的所有约束
    	Set<Restriction> reSet=getRestriction(model, property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互症状的集合
    	Set<Symptom> interSymptom=new HashSet<Symptom>();
    	 for (Restriction re : reSet) {
             for (Iterator<OntClass> i = re.listSubClasses(true); i.hasNext();) {
                 OntClass ontClass = i.next();
                 if (!ontClassSet.contains(ontClass))
                     System.out.println(ontClass.getLocalName());
                 ontClassSet.add(ontClass);
             }
         }
    	 //开始构造交互症状
    	for(OntClass ontclass:ontClassSet){
    		interSymptom.add(new Symptom(ontclass.getURI()));
    	}
    
    	return interSymptom;
    }
    public Set<Disease> getRelativeDiseaseByDisease(String property,Disease disease){
    	//找到对应的所有约束
    	Set<Restriction> reSet=getRestriction(model, property, disease.getDiseaseName());
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
    	//存放要构造成交互症状的集合
    	Set<Disease> interDisease=new HashSet<Disease>();
    	for (Restriction re : reSet) {
            for (Iterator<OntClass> i = re.listSubClasses(true); i.hasNext();) {
                OntClass ontClass = i.next();
                if (!ontClassSet.contains(ontClass))
                    System.out.println(ontClass.getLocalName());
                ontClassSet.add(ontClass);
            }
        }
    	//开始构造交互疾病
    	for(OntClass ontclass:ontClassSet){
    		interDisease.add(new Disease(ontclass.getURI()));
    	}
    	return interDisease;
    }
    
    
   



}
