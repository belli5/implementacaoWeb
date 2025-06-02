package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;

import static org.springframework.util.Assert.notNull;

public class AvaliacaoSobreClienteMapper {

    public static AvaliacaoSobreClienteJpa toAvaliacaoSobreClienteJpa(AvaliacaoSobreCliente avaliacao) {
        notNull(avaliacao, "AvaliacaoSobreCliente não pode ser nula");

        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();

        if (avaliacao.getId()!=null) {
            jpa.setId(avaliacao.getId());
        }

        jpa.setComentario(avaliacao.getComentario());
        jpa.setNota(avaliacao.getNota());
        jpa.setCliente(ClienteMapper.toClienteJpa(avaliacao.getCliente()));
        jpa.setPrestador(PrestadorMapper.toPrestadorJpa(avaliacao.getPrestador()));
        return jpa;
    }


    public static AvaliacaoSobreCliente toAvaliacaoSobreCliente(AvaliacaoSobreClienteJpa jpa) {
        notNull(jpa, "AvaliacaoSobreClienteJpa não pode ser nula");

        return new AvaliacaoSobreCliente(
                jpa.getId(),
                PrestadorMapper.toPrestador(jpa.getPrestador()),
                jpa.getComentario(),
                jpa.getNota(),
                ClienteMapper.toCliente(jpa.getCliente())
        );
    }
}
