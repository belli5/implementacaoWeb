package com.exemple.backend.dominio.strategies.prestador;

import com.exemple.backend.dominio.models.Prestador;

public interface PrestadorValidationStrategy {
    void validar(Prestador prestador);
}
