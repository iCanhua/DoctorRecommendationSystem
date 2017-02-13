package com.scut.adrs.util;

import org.ansj.library.DicLibrary;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DicInsertUtil {
	private static Logger logger = Logger.getLogger(DicInsertUtil.class);
	private static String NS = "http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#";
	@Autowired
	OntModel myOntModel;

	public DicInsertUtil() {

	}

	public DicInsertUtil(OntModel myOntModel) {
		this.myOntModel = myOntModel;
		OntClass rootSymptom = myOntModel.getOntClass(NS + "症状");
		OntClass rootBodySigns = myOntModel.getOntClass(NS + "体征");
		OntClass rootPathogeny = myOntModel.getOntClass(NS + "病因");
		OntClass rootDisease = myOntModel.getOntClass(NS + "疾病及综合症");
		ExtendedIterator<OntClass> listRootSymptomSubClasses = rootSymptom.listSubClasses();
		ExtendedIterator<OntClass> listRootBodySignsSubClasses = rootBodySigns.listSubClasses();
		ExtendedIterator<OntClass> listRootPathogenySubClasses = rootPathogeny.listSubClasses();
		ExtendedIterator<OntClass> listRootDiseaseSubClasses = rootDisease.listSubClasses();
		while (listRootSymptomSubClasses.hasNext()) {
			OntClass symptom = listRootSymptomSubClasses.next();
			String name = symptom.getURI().split("#")[1];
			DicLibrary.insert(DicLibrary.DEFAULT, name, "n", 1000);
		}
		logger.warn("症状词条录入完毕");
		while (listRootBodySignsSubClasses.hasNext()) {
			OntClass bodySigns = listRootBodySignsSubClasses.next();
			String name = bodySigns.getURI().split("#")[1];
			DicLibrary.insert(DicLibrary.DEFAULT, name, "n", 1000);
		}
		logger.warn("体征词条录入完毕");
		while (listRootPathogenySubClasses.hasNext()) {
			OntClass pathogeny = listRootPathogenySubClasses.next();
			String name = pathogeny.getURI().split("#")[1];
			DicLibrary.insert(DicLibrary.DEFAULT, name, "n", 1000);
		}
		logger.warn("病因词条录入完毕");
		while (listRootDiseaseSubClasses.hasNext()) {
			OntClass disease = listRootDiseaseSubClasses.next();
			String name = disease.getURI().split("#")[1];
			DicLibrary.insert(DicLibrary.DEFAULT, name, "n", 1000);
		}
		logger.warn("疾病词条录入完毕");
	}
}
