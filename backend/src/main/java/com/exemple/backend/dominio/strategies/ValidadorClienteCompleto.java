package com.exemple.backend.dominio.strategies;

import com.exemple.backend.dominio.models.Cliente;

public class ValidadorClienteCompleto implements ClienteValidadorStrategy {
    @Override
    public void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().length() < 3)
            throw new IllegalArgumentException("Nome inválido.");
        if (!cliente.getEmail().contains("@"))
            throw new IllegalArgumentException("Email inválido.");
        if (cliente.getTelefone() == null || cliente.getTelefone().length() < 8)
            throw new IllegalArgumentException("Telefone inválido.");
    }
}
