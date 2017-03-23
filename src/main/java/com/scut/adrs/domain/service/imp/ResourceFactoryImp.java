package com.scut.adrs.domain.service.imp;

import org.apache.jena.ontology.OntClass;

import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Resource;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.domain.service.ResourceFactory;
import com.scut.adrs.util.ontDaoUtils;

/**
 * 领域对象工程，实例化领域对象时请使用该类
 * @author FAN
 *
 */
@Service
public class ResourceFactoryImp implements ResourceFactory {
	
	public static String NS=ontDaoUtils.getNS();
    @Autowired
    protected OntModel model;
	@Override
	
	public Resource getResource(OntClass ontClass) throws Exception {
		if(ontClass!=null){
			if(ontClass.hasSuperClass(model.getOntClass(NS+"症状"))) return new Symptom(ontClass.getURI());
			if(ontClass.hasSuperClass(model.getOntClass(NS+"体征"))) return new BodySigns(ontClass.getURI());
			if(ontClass.hasSuperClass(model.getOntClass(NS+"病因"))) return new Pathogeny(ontClass.getURI());
			if(ontClass.hasSuperClass(model.getOntClass(NS+"疾病"))) return new Disease(ontClass.getURI());
			throw new Exception("不存在对应的领域对象");
		}else{
			return null;
		}
	}

}
