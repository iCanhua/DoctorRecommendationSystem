package com.scut.adrs.analyticallayer.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scut.adrs.domain.Department;
import com.scut.adrs.domain.Symptom;
import com.scut.adrs.util.OntModelUtil;

@Repository
public class SymptomDao {
	@Autowired
	OntModel myOntModel;

	static String uri = OntModelFactory.uri;

	public Symptom getSymptom(String name) {
		Symptom symptom = new Symptom();
		symptom.setSymptomName(name);
		Set<Individual> roomType = OntModelUtil.processARQGetIndividual(myOntModel, OntModelUtil.getTypes(name, "是症状"));
		Set<String> roomSet = new HashSet<String>();
		for (Individual i : roomType) {
			roomSet.add(i.getLocalName());
		}
		symptom.setRoom(roomSet);
		return symptom;
	}

	public List<Symptom> getSymptomByPosition(String position) {
		OntClass positionOntClass = myOntModel.getOntClass(uri + position);
		ArrayList<Symptom> list = new ArrayList<Symptom>();
		if (positionOntClass == null) {
			return list;
		}
		for (Iterator<OntClass> i = positionOntClass.listSubClasses(); i.hasNext();) {
			Symptom symptom = new Symptom();
			OntClass clazz = i.next();
			symptom.setSymptomName(clazz.getURI().split("#")[1]);
			Set<String> verbs = new HashSet<String>();
			verbs.add("是症状");
			Map<String, Set<String>> ontClassRestriction = OntModelUtil.getOntClassRestriction(clazz, verbs);
			for (Map.Entry<String, Set<String>> entry : ontClassRestriction.entrySet()) {
				symptom.setRoom(entry.getValue());
			}
			list.add(symptom);
		}
		return list;
	}

	public List<Department> getRooms() {
		OntClass root = myOntModel.getOntClass(uri + "科室");
		List<Department> firstDepartmentList = new ArrayList<Department>();
		Department all = new Department();
		all.setName("全部");
		all.setSubDepartment(new ArrayList<Department>());
		firstDepartmentList.add(all);
		for (Iterator<OntClass> i = root.listSubClasses(true); i.hasNext();) {
			OntClass firstDepartmentOntclass = i.next();
			Department firstDepartment = new Department();
			firstDepartment.setName(firstDepartmentOntclass.getURI().split("#")[1]);
			if (firstDepartmentOntclass.hasSubClass()) {
				List<Department> secondDepartmentList = new ArrayList<Department>();
				for (Iterator<OntClass> j = firstDepartmentOntclass.listSubClasses(true); j.hasNext();) {
					OntClass secondDepartmentOntclass = j.next();
					Department secondDepartment = new Department();
					secondDepartment.setName(secondDepartmentOntclass.getURI().split("#")[1]);
					secondDepartmentList.add(secondDepartment);
				}
				firstDepartment.setSubDepartment(secondDepartmentList);
			}
			firstDepartmentList.add(firstDepartment);
		}
		return firstDepartmentList;
	}
}
