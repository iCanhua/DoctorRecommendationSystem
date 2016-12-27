package com.scut.adrs.domain;

import java.util.List;

public class Department implements Resourse {

	@Override
	public String getIRI() {
		// TODO Auto-generated method stub
		return null;
	}

	String name;
	List<Department> subDepartment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(List<Department> sonDepartment) {
		this.subDepartment = sonDepartment;
	}

}
