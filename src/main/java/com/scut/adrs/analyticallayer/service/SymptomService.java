package com.scut.adrs.analyticallayer.service;

import java.util.List;

import com.scut.adrs.domain.Department;
import com.scut.adrs.domain.Symptom;

public interface SymptomService {
	/**
	 * 
	 * @param position
	 * @return
	 * @Description 根据部位提取症状
	 */
	public List<Symptom> getSymptomList(String position);

	/**
	 * 
	 * @return
	 * @Description 提取科室及其子科室
	 */
	public List<Department> getRooms();
}
