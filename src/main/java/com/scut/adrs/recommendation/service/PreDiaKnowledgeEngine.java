package com.scut.adrs.recommendation.service;

import java.util.Set;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.exception.UnExistURIException;
/**
 * 预诊断知识引擎接口
 * @author FAN
 */
public interface PreDiaKnowledgeEngine {
	
	public Set<Disease> getRelativeDiseaseBySymptom(Symptom symptom) throws UnExistURIException;
	
	public Set<Symptom> getRelativeSymptomByDisease(Disease disease);
	
	public Set<Pathogeny> getRelativePathogenyByDisease(Disease disease);
	
	public Set<Disease> getRelativeDiseaseByDisease(Disease disease);
	
	public Set<BodySigns> getRelativeBodySignsByDisease(Disease disease);

}
