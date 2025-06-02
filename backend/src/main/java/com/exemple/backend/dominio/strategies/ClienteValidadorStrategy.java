package com.exemple.backend.dominio.strategies;

import com.exemple.backend.dominio.models.Cliente;

public interface ClienteValidadorStrategy {
    void validar(Cliente cliente);
}
