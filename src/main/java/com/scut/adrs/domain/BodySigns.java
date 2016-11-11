package com.scut.adrs.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

public class BodySigns {
	/**
	 * 一般用RDF表示
	 */
	String bodySignName; //RDF名字

	public BodySigns(String uri) {
		super();
		this.bodySignName=uri;
	}

	public String getBodySignName() {
		return bodySignName;
	}

	public void setBodySignName(String bodySignName) {
		this.bodySignName = bodySignName;
	}


	//重写该方法，便于加入相同的症状
	public boolean equals(Object obj){
		if(!(obj instanceof BodySigns)){
			return false;
		}else{
			BodySigns bs=(BodySigns)obj;
			if(bs.getBodySignName().equals(this.bodySignName)){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){  
	    return this.bodySignName.hashCode();
	}
	
	public static void main(String[] args) {
		BodySigns a=new BodySigns("123");
		BodySigns b=new BodySigns("123");
		Set<BodySigns> set=new HashSet<BodySigns>();
		set.add(b);
		set.add(a);
		System.out.println("大小"+set.size());
	}
	
	
	
}
