package com.scut.adrs.recommendation.engine;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Doctor;
import com.scut.adrs.domain.Patient;
import com.scut.adrs.recommendation.dao.OntParserDao;
import com.scut.adrs.recommendation.service.DocMatchKnowledgeEngine;

@Service
public class OntSimilarCalculateEngine implements DocMatchKnowledgeEngine {
	@Autowired
	OntParserDao ontParserDao;

	public OntParserDao getOntParserDao() {
		return ontParserDao;
	}

	public void setOntParserDao(OntParserDao ontParserDao) {
		this.ontParserDao = ontParserDao;
	}

	@Override
	public Map<Doctor, Float> doctorMatch(Patient patient) {
		
		
		
		Map<Doctor, Float> map = new HashMap<Doctor, Float>();
		// System.out.println("找医生");
		Doctor doctor1 = new Doctor(OntParserDao.NS + "刘烈");
		Doctor doctor2 = new Doctor(OntParserDao.NS + "张曹进");
		Doctor doctor3 = new Doctor(OntParserDao.NS + "林展翼");
		Doctor doctor4 = new Doctor(OntParserDao.NS + "吴书林");
		map.put(doctor4, 23.02F);
		map.put(doctor2, 53.02F);
		map.put(doctor3, 2.02F);
		map.put(doctor1, 13.02F);
		return map;
	}
	public static void main(String[] args) {
		
	}

}
