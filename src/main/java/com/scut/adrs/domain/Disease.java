package com.scut.adrs.domain;

public class Disease {
	/**
	 * 一般用RDF表示
	 */
	String diseaseName;//RDF名字

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Disease(String diseaseName) {
		super();
		this.diseaseName = diseaseName;
	}
	
	
	

}
