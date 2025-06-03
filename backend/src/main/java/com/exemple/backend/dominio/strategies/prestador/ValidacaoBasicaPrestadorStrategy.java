package com.exemple.backend.dominio.strategies.prestador;

import com.exemple.backend.dominio.models.Prestador;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

public class ValidacaoBasicaPrestadorStrategy implements PrestadorValidationStrategy {
    @Override
    public void validar(Prestador prestador) {
        notNull(prestador, "Prestador não pode ser nulo");
        hasText(prestador.getNome(), "Nome é obrigatório");
        hasText(prestador.getEmail(), "Email é obrigatório");
        hasText(prestador.getTelefone(), "Telefone é obrigatório");
    }
}
