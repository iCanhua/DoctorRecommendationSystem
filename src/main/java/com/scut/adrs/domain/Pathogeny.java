package com.scut.adrs.domain;

public class Pathogeny implements Resourse{
	/**
	 * 一般用RDF表示
	 */
	String pathogenyName;//RDF名字

	public String getPathogenyName() {
		return pathogenyName;
	}

	public void setPathogenyName(String pathogenyName) {
		this.pathogenyName = pathogenyName;
	}

	public Pathogeny(String pathogenyName) {
		super();
		this.pathogenyName = pathogenyName;
	}
	
	//重写该方法，便于加入相同的症状
		public boolean equals(Object obj){
			if(!(obj instanceof Pathogeny)){
				return false;
			}else{
				Pathogeny sp=(Pathogeny)obj;
				if(sp.getPathogenyName().equals(this.getPathogenyName())){
					return true;
				}
			}
			return false;
		}
		
		public int hashCode(){  
		    return this.pathogenyName.hashCode();
		}

		@Override
		public String getIRI() {
			// TODO Auto-generated method stub
			return this.pathogenyName;
		}
	
	
	
}
