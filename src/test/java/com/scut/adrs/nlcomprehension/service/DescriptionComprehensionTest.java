package com.scut.adrs.nlcomprehension.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.ansj.domain.Result;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scut.adrs.domain.Resource;
import com.scut.adrs.nlcomprehension.DescriptionComprehension;
import com.scut.adrs.nlcomprehension.InterConceptQuestion;
import com.scut.adrs.nlcomprehension.service.imp.AnsjDescriptionParser;
import com.scut.adrs.nlcomprehension.service.imp.ConceptMatch;

public class DescriptionComprehensionTest {

	@Before
	public void setUp() throws Exception {
	} 

	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring*.xml");
		AnsjDescriptionParser parser = ctx.getBean(AnsjDescriptionParser.class);
		String description = "心有点绞痛，黑色的大便";
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
//		for (Resource re : question.getBodySigns()) {
//			System.out.println("第1个输出：" + re.getIRI());
//		}
//		for (Resource re : question.getMedicalHistory()) {
//			System.out.println("第2个输出：" + re.getIRI());
//		}
//		for (Resource re : question.getPathogeny()) {
//			System.out.println("第3个输出：" + re.getIRI());
//		}
//		for (Resource re : question.getSymptoms()) {
//			System.out.println("第4个输出：" + re.getIRI());
//		}
		for (Resource re : question.getHasBodySigns()) {
			System.out.println("第5个输出：" + re.getIRI());
		}
//		for (Resource re : question.getHasMedicalHistory()) {
//			System.out.println("第6个输出：" + re.getIRI());
//		}
//		for (Resource re : question.getHasPathogeny()) {
//			System.out.println("第7个输出：" + re.getIRI());
//		}
		for (Resource re : question.getHasSymptoms()) {
			System.out.println("第8个输出：" + re.getIRI());
		}
	}

}
