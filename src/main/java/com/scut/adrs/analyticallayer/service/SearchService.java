package com.scut.adrs.analyticallayer.service;

import java.util.Set;

import com.scut.adrs.domain.Patient;

public interface SearchService {
	public Patient searchInfo(Set<String> infos);
}
