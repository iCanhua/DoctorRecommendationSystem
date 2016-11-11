package com.scut.adrs.recommendation;
import java.util.List;
import java.util.Set;
import org.apache.jena.ontology.Restriction;
import com.scut.adrs.domain.BodySigns;
import com.scut.adrs.domain.Disease;
import com.scut.adrs.domain.Pathogeny;
import com.scut.adrs.domain.Symptom;
/**
 * 和病人交互会话的对象类
 * @author FAN
 *
 */
public class InterQuestion {
	private Set<Symptom> hasSymptoms;//是否拥有症状
	private Set<BodySigns> hasBodySigns;//是否拥有体征
	private Set<Pathogeny> hasPathogeny;//是否拥有病因
	private Set<Disease> hasMedicalHistory;//是否拥有病史
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
