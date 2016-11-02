package com.scut.adrs.analyticallayer.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SymptomDao {
	@Autowired
	OntModel myOntModel;

	static String uri = OntModelFactory.uri;

	public List<String> getSymptomByPosition(String position) {
		System.out.println(position);
		OntClass positionOntClass = myOntModel.getOntClass(uri + position);
		ArrayList<String> list = new ArrayList<String>();
		if (positionOntClass == null) {
			return list;
		}
		for (Iterator<OntClass> i = positionOntClass.listSubClasses(); i
				.hasNext();) {
			list.add(i.next().getLocalName());
		}
		return list;
	}
}
