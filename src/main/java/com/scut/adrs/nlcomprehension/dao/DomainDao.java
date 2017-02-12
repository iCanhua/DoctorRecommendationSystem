package com.scut.adrs.nlcomprehension.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

import org.ansj.domain.Term;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.Symptom;

public interface DomainDao {
	//获取所有症状
		public Set<Symptom> getAllSymptom();
		public Set<BodySigns> getAllBodySigns();
		public Set<Pathogeny> getAllPathogeny();
		public Set<Disease> getAllDisease();
		public Resource getResource(Term term);
}
