package com.scut.adrs.domain;

public interface Resource {
	public String getIRI();
	/**
	 * 返回领域对象类型
	 * @return
	 */
	public String getDomainType();
	/**
	 * 返回领域对象的名字
	 * @return
	 */
	public String getLocalName();
}
