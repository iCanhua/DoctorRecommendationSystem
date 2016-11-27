package com.scut.adrs.recommendation.dao;

import org.apache.jena.ontology.*;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.PreDiaKnowledgeEngine;
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
public class OntParserDao{
    public static String NS=ontDaoUtils.getNS();
    @Autowired
    private OntModel model;
    
    public OntModel getModel() {
		return model;
	}
	public void setModel(OntModel model) {
		this.model = model;
	}
	
    public static String getNS() {
		return NS;
	}
	public static void setNS(String nS) {
		NS = nS;
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
    
    public  List<Restriction> parseRestriction (String rdfStr) throws UnExistURIException {
    	if(rdfStr==null||model==null){
    		return null;
    	}
    	List<Restriction> reSet=new ArrayList<Restriction>();
        OntClass ontClass=model.getOntClass(rdfStr);
        if(ontClass==null){
        	throw new UnExistURIException(rdfStr+"    无法找到该本体");
        }
        Iterator it =ontClass.listSuperClasses(true);
        while (it.hasNext()){
            OntClass sub=(OntClass)it.next();
            if(!sub.isRestriction()){
            }else {
                Restriction re=sub.asRestriction();
                if(re.isSomeValuesFromRestriction()){
                	reSet.add(re);
                }
                if(re.isAllValuesFromRestriction()){
                	reSet.add(re);
                }
            }
        }
        return reSet;
    }

    /**
     * 构造约束
     * 通过指定属性和值来获得约束集合；
     */
    public Set<Restriction> getRestriction( String property, String valuesFrom) {
    	if(!property.contains(NS)){
    		property=NS+property;
    	}
    	if(!valuesFrom.contains(NS)){
    		valuesFrom=NS+valuesFrom;
    	}
    	
		Set<Restriction> restrictionSet = new HashSet<Restriction>();
		OntProperty p = model.getOntProperty(property);
		Iterator<Restriction> i = p.listReferringRestrictions();
		while (i.hasNext()) {
			Restriction r = i.next();
			if (r.isSomeValuesFromRestriction()) {
				if (r.asSomeValuesFromRestriction().getSomeValuesFrom().getURI().equals(valuesFrom)) {
					//System.out.println("这里捕捉到一个约束与疾病"+values+"同名");
					restrictionSet.add(r);
				}
			}
			if (r.isAllValuesFromRestriction()) {
				if (r.asAllValuesFromRestriction().getAllValuesFrom().getURI().equals(valuesFrom)) {
					restrictionSet.add(r);
				}
			}
		}
		return restrictionSet;
	}
    public Set<Restriction> getRestriction( String property) {
    	if(!property.contains(NS)){
    		property=NS+property;
    	}
    	
		Set<Restriction> restrictionSet = new HashSet<Restriction>();
		OntProperty p = model.getOntProperty( property);
		Iterator<Restriction> i = p.listReferringRestrictions();
		while (i.hasNext()) {
			Restriction r = i.next();
			if (r.isSomeValuesFromRestriction()) {
				restrictionSet.add(r);
			}
			if (r.isAllValuesFromRestriction()) {
				restrictionSet.add(r);
			}
		}
		return restrictionSet;
	}
    
    public Set<OntClass> getSubClass(Restriction re){
    	//存放所有约束相关的子类的集合
    	Set<OntClass> ontClassSet = new HashSet<OntClass>();
        for (Iterator<OntClass> i = re.listSubClasses(true); i.hasNext();) {
            OntClass ontClass = i.next();
            ontClassSet.add(ontClass);
        }
    	return ontClassSet;
    }
    public  Set<OntClass> getSuperClass(boolean isDirect,String uri){
    	if(!uri.contains(NS)){
    		uri=NS+uri;
    	}
        OntClass uriClass=model.getOntClass(uri);
        Set<OntClass> superSet=new HashSet<OntClass>();
        for (Iterator<OntClass> i =uriClass.listSuperClasses(isDirect);i.hasNext();){
            OntClass ontClass = i.next();
            //System.out.println("找父类："+ontClass.getURI());
            superSet.add(ontClass);
        }
        return superSet;
    }

    /**
	 * 抽取共同部分代码，判断某个本体是否为superURI的之类
	 * @param superRdf
	 * @param ontClass
	 * @return
	 */
	public boolean isSuperClass(String superURI,OntClass ontClass){
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
        OntModel model= ontDaoUtils.getModel();
        //classSum(model);
        //parseRestriction(model,"全身不适");
        //getRestriction(model,model.getOntProperty(ontDaoUtils.getNS()+"擅长"),model.getResource(ontDaoUtils.getNS()+"心肌病"));
    }
    
   



}
