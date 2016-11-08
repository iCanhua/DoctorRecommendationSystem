package com.scut.adrs.domain;

public class Pathogeny {
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
	
	
	
	
}
