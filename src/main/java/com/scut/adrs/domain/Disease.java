package com.scut.adrs.domain;

public class Disease extends AbstractConcept{
	/**
	 * 一般用RDF表示
	 */
	String diseaseName;// RDF名字
	String introduction;

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Disease(String diseaseName) {
		super();
		this.diseaseName = diseaseName;
	}

	@Override
	public String getIRI() {
		return this.diseaseName;
	}

	@Override
	public String getDomainType() {
		return "疾病及综合症";
	}
}
