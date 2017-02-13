package com.scut.adrs.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

public class BodySigns extends AbstractConcept{
	/**
	 * 一般用RDF表示
	 */
	String bodySignName; //RDF名字

	public BodySigns(String uri) {
		super();
		this.bodySignName=uri;
	}

	public String getBodySignName() {
		return bodySignName;
	}

	public void setBodySignName(String bodySignName) {
		this.bodySignName = bodySignName;
	}

	public static void main(String[] args) {
		BodySigns a=new BodySigns("123");
		BodySigns b=new BodySigns("123");
		Set<BodySigns> set=new HashSet<BodySigns>();
		set.add(b);
		set.add(a);
		System.out.println("大小"+set.size());
	}

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.bodySignName;
	}

	@Override
	public String getDomainType() {
		// TODO Auto-generated method stub
		return "体征";
	}
	
	
	
}
