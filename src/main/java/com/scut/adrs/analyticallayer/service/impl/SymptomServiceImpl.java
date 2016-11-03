package com.scut.adrs.analyticallayer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scut.adrs.analyticallayer.dao.SymptomDao;
import com.scut.adrs.analyticallayer.service.SymptomService;

@Service
public class SymptomServiceImpl implements SymptomService {

	@Autowired
	SymptomDao symptomDao;

	@Override
	public List<String> getSymptomList(String position) {
		List<String> list = symptomDao.getSymptomByPosition(position);
		return list;
	}
}
