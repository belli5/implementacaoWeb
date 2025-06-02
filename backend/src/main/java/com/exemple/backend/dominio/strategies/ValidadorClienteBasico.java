package com.exemple.backend.dominio.strategies;

import com.exemple.backend.dominio.models.Cliente;

public class ValidadorClienteBasico implements ClienteValidadorStrategy {
    @Override
    public void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter ao menos 3 letras.");
        }
    }
}
