package com.exemple.backend;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Aponta para a pasta 'features' em src/test/resources
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.exemple.backend.steps") // Aponta para o pacote das suas Step Definitions
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "classpath:features") // Opcional, para clareza
@AutoConfigureTestDatabase
public class CucumberRunner {
    // Esta classe não precisa de nenhum corpo. Ela serve como ponto de entrada para os testes Cucumber.
}