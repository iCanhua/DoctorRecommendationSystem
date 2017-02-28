package com.scut.adrs.nlcomprehension.service.imp;


import static org.junit.Assert.*;

import org.ansj.domain.Result;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnsjDescriptionParserTest {
	ApplicationContext ctx;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("spring/spring*.xml");
	}

	@Test
	public void test() {
		AnsjDescriptionParser parser=ctx.getBean(AnsjDescriptionParser.class);
		Result result=parser.parse("我的心绞痛");
		System.out.println(result);
		assertEquals(1, 1);
		
	}

}
