package com.scut.adrs.domain;
import java.util.List;
import java.util.Set;

import org.apache.jena.ontology.Restriction;
/**
 * 和病人交互会话的对象类
 * @author FAN
 *
 */
public class InterQuestion {
	protected Set<Symptom> hasSymptoms;//是否拥有症状
	protected Set<BodySigns> hasBodySigns;//是否拥有体征
	protected Set<Pathogeny> hasPathogeny;//是否拥有病因
	protected Set<Disease> hasMedicalHistory;//是否拥有病史
	public Set<Symptom> getHasSymptoms() {
		return hasSymptoms;
	}
	public void setHasSymptoms(Set<Symptom> hasSymptoms) {
		this.hasSymptoms = hasSymptoms;
	}
	public Set<BodySigns> getHasBodySigns() {
		return hasBodySigns;
	}
	public void setHasBodySigns(Set<BodySigns> hasBodySigns) {
		this.hasBodySigns = hasBodySigns;
	}
	public Set<Pathogeny> getHasPathogeny() {
		return hasPathogeny;
	}
	public void setHasPathogeny(Set<Pathogeny> hasPathogeny) {
		this.hasPathogeny = hasPathogeny;
	}
	public Set<Disease> getHasMedicalHistory() {
		return hasMedicalHistory;
	}
	public void setHasMedicalHistory(Set<Disease> hasMedicalHistory) {
		this.hasMedicalHistory = hasMedicalHistory;
	}
}
