package com.scut.adrs.nlcomprehension.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Resource;
import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.nlcomprehension.service.Match;
import com.scut.adrs.recommendation.engine.CosinSimTool;

@Service
public class ConceptMatch implements Match {
	@Autowired
	DomainDao domainDao;

	@Override
	public ArrayList<Resource> resourseMatch(Result result) {
		ArrayList<Resource> resourceList = new ArrayList<Resource>();
		List<Term> terms = result.getTerms();
		for (Term term : terms) {
			Resource re = domainDao.getResource(term);
			if (re != null) {
				resourceList.add(re);
			}
		}
		return resourceList;
	}

	@Override
	public ArrayList<Resource> approximateMatch(String description, Result result) {
		int N = 10;// 最高匹配10个
		ArrayList<Resource> resourceList = new ArrayList<Resource>();
		Map<Resource, Float> sortMap = new HashMap<Resource, Float>();
		Set<Resource> resourceSet = new HashSet<Resource>();
		resourceSet.addAll(domainDao.getAllBodySigns());
		resourceSet.addAll(domainDao.getAllDisease());
		resourceSet.addAll(domainDao.getAllPathogeny());
		resourceSet.addAll(domainDao.getAllSymptom());

		List<Term> terms = result.getTerms();
		for (Term term : terms) {
			for (Resource re : resourceSet) {
				Float S = new CosinSimTool(constructStrArray(term.getName()), constructStrArray(re.getLocalName()))
						.sim().floatValue();
				String comment = domainDao.getComment(re);
				if (S > 0 && comment != null && !"".equals(comment)) {

					S = S + new CosinSimTool(constructStrArray(description), constructStrArray(comment)).sim()
							.floatValue();
					sortMap.put(re, S);
				}

			}
		}
		for (Resource newRe : sortAndLimited(sortMap, 10).keySet()) {
			resourceList.add(newRe);
			System.out.println(newRe.getLocalName());
			System.out.println(sortMap.get(newRe));
		}

		return resourceList;
	}

	private static ArrayList<String> constructStrArray(String str) {
		ArrayList<String> strList = new ArrayList<String>();

		char[] array = str.toCharArray();
		for (char ch : array) {
			strList.add(String.valueOf(ch));
		}
		return strList;
	}

	private Map<Resource, Float> sortAndLimited(Map<Resource, Float> resourceAndIndex, int Limited) {
		// List<Entry<Disease,Float>> list=new ArrayList<>();
		List<Map.Entry<Resource, Float>> list = new LinkedList<Map.Entry<Resource, Float>>(resourceAndIndex.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Resource, Float>>() {
			@Override
			public int compare(Entry<Resource, Float> arg0, Entry<Resource, Float> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		for (Entry<Resource, Float> entry : list) {
			// entry.setValue(10000F);
			Limited--;
			if (Limited >= 0)
				continue;
			// System.out.println("排序后："+entry.getKey().getDiseaseName()+entry.getValue());
			resourceAndIndex.remove(entry.getKey());

		}
		return resourceAndIndex;
	}

}
