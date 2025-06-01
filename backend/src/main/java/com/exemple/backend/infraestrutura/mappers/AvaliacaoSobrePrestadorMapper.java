package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;

import static org.springframework.util.Assert.notNull;

public class AvaliacaoSobrePrestadorMapper {

    public static AvaliacaoSobrePrestadorJpa toAvaliacaoSobrePrestadorJpa(AvaliacaoSobrePrestador avaliacao) {
        notNull(avaliacao, "AvaliacaoSobrePrestador não pode ser nula");

        AvaliacaoSobrePrestadorJpa jpa = new AvaliacaoSobrePrestadorJpa();
        jpa.setId(avaliacao.getId());
        jpa.setComentario(avaliacao.getComentario());
        jpa.setNota(avaliacao.getNota());
        jpa.setCliente(null);
        jpa.setPrestador(null);
        return jpa;
    }

    public static AvaliacaoSobrePrestador toAvaliacaoSobrePrestador(AvaliacaoSobrePrestadorJpa jpa) {
        notNull(jpa, "AvaliacaoSobrePrestadorJpa não pode ser nula");

        return new AvaliacaoSobrePrestador(
                jpa.getId(),
                null,
                jpa.getComentario(),
                jpa.getNota(),
                null
        );
    }
}
