package com.exemple.backend.config;

import com.exemple.backend.dominio.strategies.prestador.PrestadorValidationStrategy;
import com.exemple.backend.dominio.strategies.prestador.ValidacaoComSenhaFortePrestadorStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrestadorValidationConfig {

    @Bean
    public PrestadorValidationStrategy prestadorValidationStrategy() {
        return new ValidacaoComSenhaFortePrestadorStrategy();
    }
}
