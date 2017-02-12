package com.scut.adrs.nlcomprehension.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.nlcomprehension.service.Match;

@Service
public class ConceptMatch implements Match {
	@Autowired
	DomainDao domainDao;
	@Override
	public ArrayList<Resource> resourseMatch(Result result) { 
		ArrayList<Resource> resourceList=new ArrayList<Resource>();
//		Set<Resource> resourceSet=new HashSet<Resource>();
//		resourceSet.addAll(domainDao.getAllBodySigns());
//		resourceSet.addAll(domainDao.getAllDisease());
//		resourceSet.addAll(domainDao.getAllPathogeny());
//		resourceSet.addAll(domainDao.getAllSymptom());
		List<Term> terms=result.getTerms();
		for(Term term:terms){
			Resource re=domainDao.getResource(term);
			if(re!=null){
				resourceList.add(re);
			}
		}
		return resourceList;
	}
	@Override
	public ArrayList<Resource> approximateMatch(Result result) {
		ArrayList<Resource> resourceList=new ArrayList<Resource>();
		resourceList.add(new Disease("心包炎？？接口数据"));
		resourceList.add(new Symptom("发热？？？接口数据"));
		resourceList.add(new Pathogeny("病因？？？接口数据"));
		resourceList.add(new BodySigns("体征？？？接口数据"));
		// TODO Auto-generated method stub
		return resourceList;
	}

}
