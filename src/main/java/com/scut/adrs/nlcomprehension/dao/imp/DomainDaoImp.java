package com.scut.adrs.nlcomprehension.dao.imp;

import org.ansj.domain.Term;
import org.apache.jena.ontology.OntClass;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.nlcomprehension.dao.DomainDao;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.service.AbstractOntParserDao;
@Service
public class DomainDaoImp extends AbstractOntParserDao implements DomainDao{
	
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

}
