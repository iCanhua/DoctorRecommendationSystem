package com.scut.adrs.domain;

public class Doctor implements Resourse{
	// 用URI表示其唯一实例
	public String name;
	public String profession;
	public String field;
	public String expert;
	public String introduction;

	public Doctor(String doctorName) {
		name = doctorName;
	}

	public Doctor(String name, String profession, String field, String expert) {
		super();
		this.name = name;
		this.profession = profession;
		this.field = field;
		this.expert = expert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	//重写该方法，便于加入相同的症状
	public boolean equals(Object obj){
		if(!(obj instanceof Doctor)){
			return false;
		}else{
			Doctor sp=(Doctor)obj;
			if(sp.getName().equals(this.getName())){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){  
	    return this.getName().hashCode();
	}

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
