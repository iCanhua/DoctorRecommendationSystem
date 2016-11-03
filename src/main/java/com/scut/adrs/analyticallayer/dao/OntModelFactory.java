package com.scut.adrs.analyticallayer.dao;

import java.io.InputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntModelFactory {
	public static String uri = "http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#";

	public static final OntModel getOntModel() {
		OntModel ontModel = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF);
		InputStream in = FileManager.get().open("11.2.owl");
		ontModel.read(in, null);
		return ontModel;
	}
}
