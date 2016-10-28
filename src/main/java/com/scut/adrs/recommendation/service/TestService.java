package com.scut.adrs.recommendation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.scut.adrs.model.Doctor;

@Service
public class TestService {

	public List<Doctor> getDoctor() {
		List<Doctor> list = new ArrayList<Doctor>();
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		list.add(new Doctor("于丹青", "主治医生", "外科 神经外科", "心血管疾病，冠心病"));
		return list;
	}
}
