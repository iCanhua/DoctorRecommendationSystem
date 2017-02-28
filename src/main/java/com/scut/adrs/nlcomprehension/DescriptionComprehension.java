package com.scut.adrs.nlcomprehension;

import java.util.ArrayList;

import org.ansj.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.nlcomprehension.service.DescriptionParser;
import com.scut.adrs.nlcomprehension.service.Match;
import com.scut.adrs.nlcomprehension.service.imp.AnsjDescriptionParser;
import com.scut.adrs.nlcomprehension.service.imp.ConceptMatch;
import com.scut.adrs.util.FileUtil;

@Service
public class DescriptionComprehension implements DescriptionParser, Match {

	@Autowired
	DescriptionParser parser;
	@Autowired
	ConceptMatch matcher;

	@Override
	public Result parse(String description) {
		return parser.parse(description);
	}

	@Override
	public ArrayList<Resource> resourseMatch(Result result) {

		return matcher.resourseMatch(result);
	}

	@Override
	public ArrayList<Resource> approximateMatch(String description,Result result) {

		return matcher.approximateMatch(description,result);
	}

	public InterConceptQuestion comprehend(String description) {
		
		InterConceptQuestion interConceptQuestion = new InterConceptQuestion();
		Result result = parser.parse(description);
		ArrayList<Resource> reasourceList = matcher.resourseMatch(result);
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

		reasourceList = matcher.approximateMatch(description,result);
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

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring*.xml");
		AnsjDescriptionParser parser = ctx.getBean(AnsjDescriptionParser.class);
		String description = "我测试，你呢，这是我心绞痛第心尖冲动一亚急性渗液缩窄性心包炎次心肌病短剧哦。心悸";
		Result result = parser.parse(description).recognition(parser.getStopRecongnition());

		System.out.println("分词结果：" + result);

		ConceptMatch matcher = ctx.getBean(ConceptMatch.class);
		ArrayList<Resource> reList = matcher.resourseMatch(result);
		for (Resource re : reList) {
			System.out.println(re.getIRI() + "该Resource类型为：" + re.getDomainType());
		}
		System.out.println("..................................");
		DescriptionComprehension dComprehension = ctx.getBean(DescriptionComprehension.class);
		InterConceptQuestion question = dComprehension.comprehend(description);
		for (Resource re : question.getBodySigns()) {
			System.out.println("第1个输出：" + re.getIRI());
		}
		for (Resource re : question.getMedicalHistory()) {
			System.out.println("第2个输出：" + re.getIRI());
		}
		for (Resource re : question.getPathogeny()) {
			System.out.println("第3个输出：" + re.getIRI());
		}
		for (Resource re : question.getSymptoms()) {
			System.out.println("第4个输出：" + re.getIRI());
		}
		for (Resource re : question.getHasBodySigns()) {
			System.out.println("第5个输出：" + re.getIRI());
		}
		for (Resource re : question.getHasMedicalHistory()) {
			System.out.println("第6个输出：" + re.getIRI());
		}
		for (Resource re : question.getHasPathogeny()) {
			System.out.println("第7个输出：" + re.getIRI());
		}
		for (Resource re : question.getHasSymptoms()) {
			System.out.println("第8个输出：" + re.getIRI());
		}

	}
}
