package com.exemple.implementacaoweb2.strategy;

import java.time.LocalDateTime;

public interface VerificacaoDisponibilidadeStrategy {
    boolean estaDisponivel(int prestadorId, LocalDateTime data);
}
