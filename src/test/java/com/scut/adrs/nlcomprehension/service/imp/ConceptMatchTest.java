package com.scut.adrs.nlcomprehension.service.imp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fnlp.util.exception.LoadModelException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scut.adrs.domain.Resource;
import com.scut.adrs.nlcomprehension.service.Match;

public class ConceptMatchTest {

	ApplicationContext ctx;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("spring/spring*.xml");
	}

//	@Test
//	public void test() throws LoadModelException {
//		Match match=ctx.getBean(ConceptMatch.class);
//		AnsjDescriptionParser parser=ctx.getBean(AnsjDescriptionParser.class);
//		String str="黑色的大便，心有点绞痛";
////		System.out.println(parser.parse(str));
//		ArrayList<Resource> list=match.approximateMatch(str,parser.parse(str));
//		for(Resource re:list){
//			//System.out.println(re.getLocalName());
//		}
//	}
	@Test
	public void testKG() throws LoadModelException{
		Match match=ctx.getBean(ConceptMatch.class);
		AnsjDescriptionParser parser=ctx.getBean(AnsjDescriptionParser.class);
		String str="黑色的大便，心脏有点绞痛,脾这个部位增大";
		System.out.println("分词结果"+parser.parse(str));
		match.approximateMatch(str, parser.parse(str));
	}

}
