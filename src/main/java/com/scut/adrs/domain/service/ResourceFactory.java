package com.scut.adrs.domain.service;

import org.apache.jena.ontology.OntClass;

import com.scut.adrs.domain.Resource;

public interface ResourceFactory {
	public Resource getResource(OntClass ontClass)throws Exception;
}
