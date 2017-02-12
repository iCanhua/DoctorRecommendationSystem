package com.scut.adrs.domain;

public class Pathogeny extends AbstractConcept{
	/**
	 * 一般用RDF表示
	 */
	String pathogenyName;//RDF名字

	public String getPathogenyName() {
		return pathogenyName;
	}

	public void setPathogenyName(String pathogenyName) {
		this.pathogenyName = pathogenyName;
	}

	public Pathogeny(String pathogenyName) {
		super();
		this.pathogenyName = pathogenyName;
	}
	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.pathogenyName;
	}

	@Override
	public String getDomainType() {
		// TODO Auto-generated method stub
		return "病因";
	}
	
	
	
}
