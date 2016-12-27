package com.scut.adrs.analyticallayer.service;

import com.scut.adrs.domain.Doctor;

public interface DoctorService {
	public String getIntroduction(String uri);

	public Doctor getDoctorData(String name);
}
