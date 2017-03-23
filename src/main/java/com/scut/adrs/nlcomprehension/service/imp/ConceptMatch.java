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
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.Resource;
import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.nlcomprehension.service.Match;
import com.scut.adrs.recommendation.engine.CosinSimTool;
/**
 * 匹配实现
 * @author FAN
 *
 */
@Service
public class ConceptMatch implements Match {
	@Autowired
	DomainDao domainDao;
	
	/**
	 * 精确匹配，结果是描述中对应知识库中存在的所有实例
	 */
	@Override
	public Set<Resource> accuratelyMatch(String description,Result result) {
		HashSet<Resource> resourceList = new HashSet<Resource>();
		List<Term> terms = result.getTerms();
		for (Term term : terms) {
			Resource re = domainDao.getResource(term);
			if (re != null) {
				resourceList.add(re);
			}
		}
		return resourceList;
	}
	/**
	 * 模糊匹配，该匹配过程分来两部分，第一是余弦相似度计算，第二是知识图谱推理，优先返回知识图谱推理结果
	 */
	@Override
	public Set<Resource> approximateMatch(String description,Result result) throws LoadModelException {
		
		Map<Resource, Float> map=this.knowledgeGraphMatch(description, result);
		for(Resource re:map.keySet()){
			System.out.println(re.getLocalName()+" 图谱匹配 "+map.get(re));
		}
		boolean solider=true;
		while(map.size()<10&&solider){
			int space=10-map.size();
			map.putAll(this.cosinMatch(description, result,space));
			//如果没有增加，则跳出
			if(space==10-map.size()){
				break;
			}
		}
		for(Resource re:map.keySet()){
			System.out.println(re.getLocalName()+" 综合匹配 "+map.get(re));
		}
		return map.keySet();
	}
	
	/**
	 * 知识图谱推理匹配
	 * @param description 用户描述
	 * @param result 分词结果
	 * @return 匹配结果
	 * @throws LoadModelException
	 */
	private Map<Resource, Float> knowledgeGraphMatch(String description,Result result) throws LoadModelException {
		CNFactory factory = CNFactory.getInstance("./src/main/resources/models");
		Map<Resource, Float> matchResult=new HashMap<Resource, Float>();
		String[] words=factory.seg(description);
		String[] pos=factory.tag(words);
		int[] tree=factory.parse(words, pos);
		System.out.println();
		//获取该树中与解剖结构相关的所有与词
		for(int i=0;i<words.length;i++){
			if(domainDao.hasAnatomical(words[i])){
				HashSet<Resource> relaResourceSet=new HashSet<Resource>();
				try {
					relaResourceSet.addAll(domainDao.getRelativeResource(words[i]));
				} catch (Exception e) {
					// 程序出错
					System.out.println("程序异常:"+this.getClass());
					e.printStackTrace();
				}
				for(int j=0;j<tree.length;j++){
					if(tree[j]==i){
						matchResult.putAll(this.cosinMatch(words[j],relaResourceSet, 5));
					}
				}
				if(tree[i]!=-1){
					matchResult.putAll(this.cosinMatch(words[tree[i]],relaResourceSet, 5));//处理自身依存
				}
			}
		}
		return matchResult;
	}
	
	
	//根绝描述词和领域对象进行匹配，匹配算法结合词和词的comment综合评测
	private Map<Resource, Float> cosinMatch(String descripWord,Set<Resource> resourceSet,int topNum){
		Map<Resource, Float> sortMap = new HashMap<Resource, Float>();
		ArrayList<String> wordArray=constructStrArray(descripWord);
		for(Resource re:resourceSet){
			Float sim=new CosinSimTool(wordArray,constructStrArray(re.getLocalName())).sim().floatValue();//计算相似度
			String comment=domainDao.getComment(re);
			if(sim>0&&comment!=null&&!"".equals(comment)){
				sim=sim+new CosinSimTool(wordArray, constructStrArray(comment)).sim().floatValue();
			}
			if(sim>0){
				sortMap.put(re, sim);
			}
		}
		return sortAndLimited(sortMap, topNum);
	}
	
	//根据描述词和分词结果进行匹配，匹配算法结合词和词的comment综合评测，算法由不同，该算法余弦相似度的粒度为词而不是字
	private Map<Resource, Float> cosinMatch(String description,Result result,int topNum) {
		Map<Resource, Float> sortMap = new HashMap<Resource, Float>();
		Set<Resource> resourceSet = new HashSet<Resource>();
		resourceSet.addAll(domainDao.getAllBodySigns());
		resourceSet.addAll(domainDao.getAllDisease());
		resourceSet.addAll(domainDao.getAllPathogeny());
		resourceSet.addAll(domainDao.getAllSymptom());
		List<Term> terms=result.getTerms();
		for(Term term:terms){
			for(Resource re:resourceSet){
				Float sim=new CosinSimTool(constructStrArray(term.getName()),constructStrArray(re.getLocalName())).sim().floatValue();
				String comment=domainDao.getComment(re);
				if(sim>0&&comment!=null&&!"".equals(comment)){
					sim=sim+new CosinSimTool(constructStrArray(description), constructStrArray(comment)).sim().floatValue();
				}
				if(sim>0.5){
					sortMap.put(re, sim);
				}
			}
		}
		return sortAndLimited(sortMap,topNum);
	}
	
	/**
	 * 构造适合余弦计算的字符序列
	 * @param str
	 * @return
	 */
	private static ArrayList<String> constructStrArray(String str) {
		ArrayList<String> strList = new ArrayList<String>();

		char[] array = str.toCharArray();
		for (char ch : array) {
			strList.add(String.valueOf(ch));
		}
		return strList;
	}
	/**
	 * 排序后截取前N个结果
	 * @param resourceAndIndex
	 * @param Limited
	 * @return
	 */
	private Map<Resource, Float> sortAndLimited(Map<Resource, Float> resourceAndIndex, int Limited) {
		List<Map.Entry<Resource, Float>> list = new LinkedList<Map.Entry<Resource, Float>>(resourceAndIndex.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Resource, Float>>() {
					@Override
			public int compare(Entry<Resource, Float> arg0, Entry<Resource, Float> arg1) {
						return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		for (Entry<Resource, Float> entry : list) {
			Limited--;
			if (Limited >= 0)
				continue;
			resourceAndIndex.remove(entry.getKey());
		}
		return resourceAndIndex;
	}
}
