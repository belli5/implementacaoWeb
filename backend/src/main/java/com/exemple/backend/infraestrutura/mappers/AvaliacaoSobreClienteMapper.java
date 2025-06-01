package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;

import static org.springframework.util.Assert.notNull;

public class AvaliacaoSobreClienteMapper {

    public static AvaliacaoSobreClienteJpa toAvaliacaoSobreClienteJpa(AvaliacaoSobreCliente avaliacao) {
        notNull(avaliacao, "AvaliacaoSobreCliente não pode ser nula");

        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(avaliacao.getId());
        jpa.setComentario(avaliacao.getComentario());
        jpa.setNota(avaliacao.getNota());
        jpa.setCliente(null);     // Evita ciclos
        jpa.setPrestador(null);   // Evita ciclos
        return jpa;
    }

    public static AvaliacaoSobreCliente toAvaliacaoSobreCliente(AvaliacaoSobreClienteJpa jpa) {
        notNull(jpa, "AvaliacaoSobreClienteJpa não pode ser nula");

        return new AvaliacaoSobreCliente(
                jpa.getId(),
                new Prestador(jpa.getPrestador().getId(), null, null, null, null, null),
                jpa.getComentario(),
                jpa.getNota(),
                new Cliente(jpa.getCliente().getId(), null, null, null, null, null)
        );
    }
}
