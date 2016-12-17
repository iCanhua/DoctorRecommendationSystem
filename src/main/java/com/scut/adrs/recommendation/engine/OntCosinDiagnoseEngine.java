package com.scut.adrs.recommendation.engine;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.scut.adrs.domain.Resourse;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.exception.UnExistURIException;
import com.scut.adrs.recommendation.service.DiagnoseKnowledgeEngine;
import com.scut.adrs.recommendation.service.PreDiaKnowledgeEngine;
@Service
public class OntCosinDiagnoseEngine implements DiagnoseKnowledgeEngine{

	@Autowired
	OntParserDao ontParserDao;
	public OntParserDao getOntParserDao() {
		return ontParserDao;
	}
	public void setOntParserDao(OntParserDao ontParserDao) {
		this.ontParserDao = ontParserDao;
	}
	@Autowired
	PreDiaKnowledgeEngine preDiaEngine;

	public PreDiaKnowledgeEngine getPreDiaEngine() {
		return preDiaEngine;
	}
	public void setPreDiaEngine(PreDiaKnowledgeEngine preDiaEngine) {
		this.preDiaEngine = preDiaEngine;
	}
	
	@Override
	public Patient defineDiseaseIndex(Patient patient) {
		if(patient==null){
			return null;
		}
		//初始化所有疾病，继承病人本来有的疾病
		Set<Disease> diseaseSet=new HashSet<Disease>();
		for(Disease ds:patient.getDiseaseAndIndex().keySet()){
			diseaseSet.add(ds);
		}
		//找到所有相关的疾病集
		List<String> recordList=new ArrayList<String>();
		Set<Symptom> sympthomSet = patient.getHasSymptoms();
		for(Symptom sy:sympthomSet){
			recordList.add(sy.getSymptomName());
		}
		Set<BodySigns> bodySigns = patient.getHasBodySigns();
		for(BodySigns bs:bodySigns){
			recordList.add(bs.getBodySignName());
		}
		Set<Pathogeny> pathogeny = patient.getHasPathogeny();
		for(Pathogeny py:pathogeny){
			recordList.add(py.getPathogenyName());
		}
		Set<Disease> disease = patient.getHasMedicalHistory();
		for(Disease ds:disease){
			recordList.add(ds.getDiseaseName());
		}
		//开始把疾病放进疾病集中
		for(String record:recordList){
			try {
				diseaseSet.addAll(getRestritionRelativeDiseaseByOntClass(record));
			} catch (UnExistURIException e) {
				continue;
			}
		}
		//对每个疾病计算匹配相似度
		for(Disease ds:diseaseSet){
			Float sim=consinIndexEvaluate(patient,ds).floatValue();
			BigDecimal b=new BigDecimal(sim);  
			sim=b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			patient.getDiseaseAndIndex().put(ds, sim);
			//System.out.println("计算了一个疾病："+ds.getDiseaseName()+"的余弦相似度："+sim);
		}
		return patient;
	}
	private Double consinIndexEvaluate(Patient patient,Disease disease){
		//复用预诊断接口，求出相关概念集，计算各个概念集的余弦相似度，其实可以不用接口，用到的疾病相关记录，可以使用之前的，从容器上下文中获取，这里可以优化！
		Float S=new CosinSimTool(constructStrArray(preDiaEngine.getRelativeSymptomByDisease(disease)), constructStrArray(patient.getHasSymptoms())).sim().floatValue();
		Float B=new CosinSimTool(constructStrArray(preDiaEngine.getRelativeBodySignsByDisease(disease)), constructStrArray(patient.getHasBodySigns())).sim().floatValue();
		Float P=new CosinSimTool(constructStrArray(preDiaEngine.getRelativePathogenyByDisease(disease)), constructStrArray(patient.getHasPathogeny())).sim().floatValue();
		Float M=new CosinSimTool(constructStrArray(preDiaEngine.getRelativeDiseaseByDisease(disease)), constructStrArray(patient.getHasMedicalHistory())).sim().floatValue();
		//System.out.println("疾病"+disease.getDiseaseName()+"的四项余弦相似度为："+S+" "+B+" "+P+" "+M);
		int W=patient.getHasSymptoms().size()+patient.getHasBodySigns().size()+patient.getHasPathogeny().size()+patient.getHasMedicalHistory().size();
		Float Ws=(float)patient.getHasSymptoms().size();
		Float Wb=(float)patient.getHasBodySigns().size();
		Float Wp=(float)patient.getHasPathogeny().size();
		Float Wm=(float)patient.getHasMedicalHistory().size();
		//System.out.println(W+" 的四项权重分别为："+Ws+" "+Wb+" "+Wp+" "+Wm);
		double sim=(Ws*S+Wb*B+Wp*P+Wm*M)/W;
		//System.out.println("未转化的的余弦相似度："+sim);
		return sim;
	}
	private ArrayList<String>  constructStrArray(Set<? extends Resourse> resourse){
		ArrayList<String> array=new ArrayList<String>();
		for(Resourse re:resourse){
			array.add(re.getIRI());
		}
		return array;
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
