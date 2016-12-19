package com.scut.adrs.domain;

public class Disease implements Resourse{
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

	// 重写该方法，便于加入相同的症状
	public boolean equals(Object obj) {
		if (!(obj instanceof Disease)) {
			return false;
		} else {
			Disease sp = (Disease) obj;
			if (sp.getDiseaseName().equals(this.getDiseaseName())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return this.diseaseName.hashCode();
	}

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.diseaseName;
	}

}
