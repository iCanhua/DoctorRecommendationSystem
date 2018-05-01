package com.scut.adrs.config;


import com.scut.adrs.recommendation.diagnose.DiagnoseKnowledgeEngine;
import com.scut.adrs.recommendation.engine.OntCosinDiagnoseEngine;
import com.scut.adrs.recommendation.engine.OntDiagnoseEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
public class JavaConfig {
    @Bean("consinEngine")
    public DiagnoseKnowledgeEngine createConsinEngine(){
        return new OntCosinDiagnoseEngine();
    }

    @Bean("dianoseEngine")
    public OntDiagnoseEngine createOntDiagnoseEngine(){
        return new OntDiagnoseEngine();

    }
}
