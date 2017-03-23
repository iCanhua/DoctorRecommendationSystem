package com.scut.adrs.nlcomprehension.service;


import java.util.Set;

import org.ansj.domain.Result;
import org.fnlp.util.exception.LoadModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.Symptom;
/**
 * 描述理解控制器，外观模式
 * @author FAN
 *
 */
@Service
public class DescriptionComprehension implements DescriptionParser, Match {

	@Autowired
	DescriptionParser parser;
	@Autowired
	Match matcher;

	@Override
	public Result parse(String description) {
		return parser.parse(description);
	}

	@Override
	public Set<Resource> approximateMatch(String description,Result result) throws LoadModelException {

		return matcher.approximateMatch(description,result);
	}
	
	/**
	 * 该方法封装NL模块的所有子系统调用顺序，并且把得到的结果进行包装返回！
	 * @param description 用户描述
	 * @return 交互确认问题
	 */
	public InterConceptQuestion comprehend(String description) {
		
		InterConceptQuestion interConceptQuestion = new InterConceptQuestion();
		Result result = parser.parse(description);
		Set<Resource> reasourceList = matcher.accuratelyMatch(description, result);
		for (Resource resource : reasourceList) {
			if ("症状".equals(resource.getDomainType()))
				interConceptQuestion.getSymptoms().add((Symptom) resource);
			if ("体征".equals(resource.getDomainType()))
				interConceptQuestion.getBodySigns().add((BodySigns) resource);
			if ("病因".equals(resource.getDomainType()))
				interConceptQuestion.getPathogeny().add((Pathogeny) resource);
			if ("疾病及综合症".equals(resource.getDomainType()))
				interConceptQuestion.getMedicalHistory().add((Disease) resource);
		}

		try {
			reasourceList = matcher.approximateMatch(description,result);
		} catch (LoadModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Resource resource : reasourceList) {
			
			if ("症状".equals(resource.getDomainType()))
				interConceptQuestion.getHasSymptoms().add((Symptom) resource);
			if ("体征".equals(resource.getDomainType()))
				interConceptQuestion.getHasBodySigns().add((BodySigns) resource);
			if ("病因".equals(resource.getDomainType()))
				interConceptQuestion.getHasPathogeny().add((Pathogeny) resource);
			if ("疾病及综合症".equals(resource.getDomainType()))
				interConceptQuestion.getHasMedicalHistory().add((Disease) resource);
		}
		return interConceptQuestion;
	}

	@Override
	public Set<Resource> accuratelyMatch(String description, Result result) {
		return matcher.accuratelyMatch(description, result);
		
	}
}
