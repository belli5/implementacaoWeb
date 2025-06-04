package com.exemple.backend.steps; // Ou o pacote onde suas classes de steps estão

import com.exemple.backend.BackendApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
@AutoConfigureTestDatabase // Garante que o banco de dados em memória seja usado
public class SpringCucumberContextConfiguration {
    // Esta classe não precisa de nenhum corpo.
    // Ela serve apenas para configurar o contexto Spring para o Cucumber.
}