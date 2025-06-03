package com.exemple.backend.dominio.strategies.prestador;

import com.exemple.backend.dominio.models.Prestador;

import static org.springframework.util.Assert.*;

public class ValidacaoComSenhaFortePrestadorStrategy implements PrestadorValidationStrategy {
    @Override
    public void validar(Prestador prestador) {
        notNull(prestador, "Prestador não pode ser nulo");
        hasText(prestador.getNome(), "Nome é obrigatório");
        hasText(prestador.getEmail(), "Email é obrigatório");
        hasText(prestador.getTelefone(), "Telefone é obrigatório");
        hasText(prestador.getSenha(), "Senha é obrigatória");

        if (prestador.getSenha().length() < 8 || !prestador.getSenha().matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Senha fraca. Deve ter pelo menos 8 caracteres e uma letra maiúscula.");
        }
    }
}
