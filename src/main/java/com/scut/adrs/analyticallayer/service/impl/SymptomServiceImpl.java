package com.scut.adrs.analyticallayer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.analyticallayer.dao.SymptomDao;
import com.scut.adrs.analyticallayer.service.SymptomService;
import com.scut.adrs.domain.Department;
import com.scut.adrs.domain.Symptom;

@Service
public class SymptomServiceImpl implements SymptomService {

	@Autowired
	SymptomDao symptomDao;

	@Override
	public List<Symptom> getSymptomList(String position) {
		List<Symptom> list = symptomDao.getSymptomByPosition(position);
		return list;
	}

	@Override
	public List<Department> getRooms() {
		List<Department> rooms = symptomDao.getRooms();
		return rooms;
	}
}
