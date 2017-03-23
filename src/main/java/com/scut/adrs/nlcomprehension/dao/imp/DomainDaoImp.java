package com.scut.adrs.nlcomprehension.dao.imp;

import java.util.HashSet;
import java.util.Set;

import org.ansj.domain.Term;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.dao.AbstractOntParserDao;
import com.scut.adrs.domain.service.ResourceFactory;
@Service
public class DomainDaoImp extends AbstractOntParserDao implements DomainDao{
	@Autowired
	ResourceFactory resourceFactory;
	@Override
	public Resource getResource(Term term) {
		OntClass ontclass= model.getOntClass(NS+term.getName());
		Resource concept;
		if(ontclass!=null){
			if(ontclass.hasSuperClass(model.getOntClass(NS+"症状"))) return new Symptom(ontclass.getURI());
			if(ontclass.hasSuperClass(model.getOntClass(NS+"体征"))) return new BodySigns(ontclass.getURI());
			if(ontclass.hasSuperClass(model.getOntClass(NS+"病因"))) return new Pathogeny(ontclass.getURI());
			if(ontclass.hasSuperClass(model.getOntClass(NS+"疾病"))) return new Disease(ontclass.getURI());
			return null;
		}else{
			return null;
		}
	}

	@Override
	public String getComment(Resource re) {
		OntClass ontclass= model.getOntClass(re.getIRI());
		return ontclass.getComment(null);
	}

	@Override
	public boolean hasAnatomical(String word) {
		OntClass ontclass= model.getOntClass(NS+word);
		if(null!=ontclass&&ontclass.hasSuperClass(model.getOntClass(NS+"疾病"))){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public HashSet<Resource> getRelativeResource(String word) throws Exception {
		HashSet<Resource> resourceSet=new HashSet<Resource>();
		OntClass ontclass= model.getOntClass(NS+word);
		if(null==ontclass){
			throw new Exception("请传入一个本体库中存在的概念词");
		}
		Set<Restriction> reSet=this.getRestriction("相关部位", NS+word);
		for(Restriction re:reSet){
			if(re.listSubClasses().hasNext()){
				OntClass relaClass=re.listSubClasses().next();
				resourceSet.add(resourceFactory.getResource(relaClass));
			}
		}
		return resourceSet;
	}

}
