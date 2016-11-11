package com.scut.adrs.util;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FAN on 2016/10/11.
 */
@Service
public class ontDaoUtils {
	static String filepath="file:/C:\\Users\\FAN\\Desktop\\医院项目\\医生推荐系统\\本体库及工具\\本体库\\正式本体库\\master\\11.8.owl";
	static String SOURSE;
    public static String getFilepath() {
		return filepath;
	}

	public static void setFilepath(String filepath) {
		ontDaoUtils.filepath = filepath;
	}
    public static  OntModel getModel(){
        OntModel  base= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        base.read(filepath);
        OntDocumentManager documentManager=base.getDocumentManager();
        base = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF, base );
        System.out.println("本体模型读取成功");
        return base;

    }

    public static String getNS(){
        SOURSE ="http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26";
        String NS=SOURSE+"#";
        return NS;
    }
    public static String getPrefix(){
        return "PREFIX ontology: <http://www.semanticweb.org/fan/ontologies/2016/7/untitled-ontology-26#>"+
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
    }
    public static String getRdfsPref(String prefix){
        if (prefix.equals("rdfs")){
            return "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>";
        }

        return null;
    }


}
