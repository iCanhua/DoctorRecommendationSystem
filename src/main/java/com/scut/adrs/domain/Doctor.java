package com.scut.adrs.domain;

import java.util.Set;

public class Doctor extends AbstractConcept {
	// 用URI表示其唯一实例

	// 医生名字
	public String name;
	// 医生介绍
	public String introduction;
	// 所属组
	public Set<String> group;
	// 擅长
	public Set<String> expert;
	// 尤其擅长
	public Set<String> sexpert;
	// 头衔
	public Set<String> title;

	public Doctor() {

	}

	public Doctor(String doctorName) {
		name = doctorName;
	}


	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<String> getGroup() {
		return group;
	}

	public void setGroup(Set<String> group) {
		this.group = group;
	}

	public Set<String> getExpert() {
		return expert;
	}

	public void setExpert(Set<String> expert) {
		this.expert = expert;
	}

	public Set<String> getTitle() {
		return title;
	}

	public void setTitle(Set<String> title) {
		this.title = title;
	}

	public Set<String> getSexpert() {
		return sexpert;
	}

	public void setSexpert(Set<String> sexpert) {
		this.sexpert = sexpert;
	}

	@Override
	public String getDomainType() {
		// TODO Auto-generated method stub
		return "医生";
	}

}
