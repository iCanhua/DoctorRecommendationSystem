package com.scut.adrs.recommendation.service;

import java.util.Set;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.recommendation.exception.UnExistRdfException;
/**
 * 预诊断知识引擎接口
 * @author FAN
 */
public interface PreDiaKnowledgeEngine {
	/**
	 * 根据症状获取该症状相关的疾病
	 * @param symptom
	 * @return 疾病集合
	 * @throws UnExistRdfException
	 */
	public Set<Disease> getRelativeDiseaseBySymptom(Symptom symptom) throws UnExistRdfException;
	/**
	 * 根据疾病获取相关的症状
	 * @param disease
	 * @return 症状集
	 */
	public Set<Symptom> getRelativeSymptomByDisease(Disease disease);
	/**
	 * 根据疾病获取相关的病因
	 * @param disease
	 * @return 病因集
	 */
	public Set<Pathogeny> getRelativePathogenyByDisease(Disease disease);
	/**
	 * 根据症状获取相关的疾病（引发，导致）
	 * @param disease
	 * @return 疾病集
	 */
	public Set<Disease> getRelativeDiseaseByDisease(Disease disease);
	/**
	 * 根据疾病获取相关的体征
	 * @param disease
	 * @return 体征集
	 */
	public Set<BodySigns> getRelativeBodySignsByDisease(Disease disease);

}
