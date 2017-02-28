package com.scut.adrs.nlcomprehension.service;

import java.util.ArrayList;

import org.ansj.domain.Result;

import com.scut.adrs.domain.Resource;

public interface Match {
	public ArrayList<Resource> resourseMatch(Result result);
	public ArrayList<Resource> approximateMatch(String description,Result result);
}
