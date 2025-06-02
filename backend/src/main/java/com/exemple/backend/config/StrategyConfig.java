package com.exemple.backend.config;

import com.exemple.backend.dominio.strategies.ClienteValidadorStrategy;
import com.exemple.backend.dominio.strategies.ValidadorClienteCompleto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

    @Bean
    public ClienteValidadorStrategy clienteValidadorStrategy() {
        return new ValidadorClienteCompleto();
    }
}
