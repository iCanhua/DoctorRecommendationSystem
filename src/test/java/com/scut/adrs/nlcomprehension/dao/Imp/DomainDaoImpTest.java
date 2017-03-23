package com.scut.adrs.nlcomprehension.dao.Imp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.nlcomprehension.dao.imp.DomainDaoImp;

public class DomainDaoImpTest {

	ApplicationContext ctx;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("spring/spring*.xml");
	}
	@Test
	public void test() {
		DomainDao dao=ctx.getBean(DomainDaoImp.class);
		try {
			dao.getRelativeResource("心脏");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
