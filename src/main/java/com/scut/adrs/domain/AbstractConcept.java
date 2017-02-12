package com.scut.adrs.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractConcept implements Resource {
	
	//重写该方法，便于加入相同的症状
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Resource)){
			return false;
		}else{
			Resource rs=(Resource)obj;
			if(rs.getIRI().equals(this.getIRI())){
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode(){  
	    return this.getIRI().hashCode();
	}
	
	public static void main(String[] args) {
		System.out.println("领域模型单元测试——抽象概念");
		Disease disease1=new Disease("df");
		Disease disease2=new Disease("df");
		Set<Disease> set=new HashSet<Disease>();
		set.add(disease1);
		set.add(disease2);
		System.out.println("类型:"+disease1.getDomainType()+"数量："+set.size());
	}

}
