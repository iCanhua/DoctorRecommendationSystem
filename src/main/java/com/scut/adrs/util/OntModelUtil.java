package com.scut.adrs.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.AllValuesFromRestriction;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.ontology.SomeValuesFromRestriction;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.iterator.ExtendedIterator;

import com.scut.adrs.analyticallayer.dao.OntModelFactory;

public class OntModelUtil {
	public static String prefix = "PREFIX  untitled-ontology-26: <http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#>"
			+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX  owl: <http://www.w3.org/2002/07/owl#>" + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	public static String uri = OntModelFactory.uri;

	/**
	 * 
	 * @param subject
	 *            实体名称
	 * @param property
	 *            属性名称
	 * @return sparql语句
	 * @Description 求Type型的属性客体
	 */
	public static String getTypes(String subject, String property) {
		String q = prefix + "SELECT ?c WHERE{{untitled-ontology-26:" + subject
				+ " rdf:type ?b. ?b owl:onProperty untitled-ontology-26:" + property + ". ?b owl:someValuesFrom ?c}"
				+ "UNION {untitled-ontology-26:" + subject + " rdf:type ?b. ?b owl:onProperty untitled-ontology-26:"
				+ property + ". ?b owl:allValuesFrom ?c}}";
		return q;
	}

	/**
	 * 
	 * @param subject
	 *            实体名称
	 * @param property
	 *            属性名称
	 * @return sparql语句
	 * @Description 求Property型的属性客体
	 */
	public static String getProperty(String subject, String property) {
		String q = prefix + "SELECT ?c WHERE{untitled-ontology-26:" + subject + " untitled-ontology-26:" + property
				+ " ?c}";
		return q;
	}

	public static String getComment(String subject) {
		String q = prefix + "SELECT ?c WHERE{untitled-ontology-26:" + subject + " rdfs:comment ?c}";
		return q;
	}

	/**
	 * 
	 * @param ontModel
	 *            ontModel对象
	 * @param q
	 *            sparql语句
	 * @return 满足sparql语句的实体集
	 * @Description 根据sparql语句寻找ontModel满足的实体集
	 */
	public static Set<Individual> processARQGetIndividual(OntModel ontModel, String q) {
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.create(query, ontModel);
		ResultSet results = qexec.execSelect();
		Set<Individual> set = new HashSet<>();
		for (; results.hasNext();) {
			QuerySolution solution = results.next();
			RDFNode node = solution.get("c");
			if (node.canAs(Individual.class)) {
				Individual individual = node.as(Individual.class);
				set.add(individual);
			}
		}
		return set;
	}

	/**
	 * 
	 * @param ontModel
	 *            ontModel对象
	 * @param q
	 *            sparql语句
	 * @return 满足sparql语句的文本集
	 * @Description 根据sparql语句寻找ontModel满足的文本集
	 */
	public static Set<Literal> processARQGetLiteral(OntModel ontModel, String q) {
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.create(query, ontModel);
		ResultSet results = qexec.execSelect();
		Set<Literal> set = new HashSet<>();
		for (; results.hasNext();) {
			QuerySolution solution = results.next();
			RDFNode node = solution.get("c");
			if (node.canAs(Literal.class)) {
				Literal literal = node.as(Literal.class);
				set.add(literal);
			}
		}
		return set;
	}

	/**
	 * 
	 * @param ontClass
	 *            要求的本体类
	 * @param verbs
	 *            要求的谓词集合
	 * @return 谓词和宾语的一对多数据结构
	 * @Description 从已知的本体类和谓词集合求本体类的所有相关约束
	 */
	public static Map<String, Set<String>> getOntClassRestriction(OntClass ontClass, Set<String> verbs) {
		// 一对多数据结构
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		// 将所求谓词全部添加进来
		for (String verb : verbs) {
			map.put(verb, new HashSet<String>());
		}
		ExtendedIterator<OntClass> iterator = ontClass.listSuperClasses();
		while (iterator.hasNext()) {
			OntClass type = iterator.next();
			if (type.canAs(Restriction.class)) {
				Restriction restriction = type.as(Restriction.class);
				String restrictionVerb = "";
				String restrictionObject = "";
				if (restriction.isSomeValuesFromRestriction()) {
					SomeValuesFromRestriction asSomeValuesFromRestriction = restriction.asSomeValuesFromRestriction();
					restrictionVerb = asSomeValuesFromRestriction.getOnProperty().getURI().split("#")[1];
					restrictionObject = asSomeValuesFromRestriction.getSomeValuesFrom().getURI().split("#")[1];
				}
				if (restriction.isAllValuesFromRestriction()) {
					AllValuesFromRestriction asAllValuesFromRestriction = restriction.asAllValuesFromRestriction();
					restrictionVerb = asAllValuesFromRestriction.getOnProperty().getURI().split("#")[1];
					restrictionObject = asAllValuesFromRestriction.getAllValuesFrom().getURI().split("#")[1];
				}
				if (verbs.contains(restrictionVerb)) {
					// 将谓词宾语添加进来
					map.get(restrictionVerb).add(restrictionObject);
				}
			}
		}
		return map;
	}
}
