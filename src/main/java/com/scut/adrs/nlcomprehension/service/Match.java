package com.scut.adrs.nlcomprehension.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.ansj.domain.Result;
import org.fnlp.util.exception.LoadModelException;

import com.scut.adrs.domain.Resource;


/**
 * 匹配接口，分为精确匹配和模糊匹配
 * @author FAN
 *
 */
public interface Match {
	public Set<Resource>  accuratelyMatch(String description,Result result);
	public Set<Resource> approximateMatch(String description,Result result) throws LoadModelException;
	
}
