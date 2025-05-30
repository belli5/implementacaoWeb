package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;

import static org.springframework.util.Assert.notNull;

public class OfereceMapper {

    public static OfereceJpa toOfereceJpa(Oferece oferece) {
        notNull(oferece, "Oferece não pode ser nulo");

        return new OfereceJpa(
                oferece.getId(),
                PrestadorMapper.toPrestadorJpa(oferece.getPrestador()),
                ServicoMapper.toServicoJpa(oferece.getServico())
        );
    }

    public static Oferece toOferece(OfereceJpa ofereceJpa) {
        notNull(ofereceJpa, "OfereceJpa não pode ser nulo");

        return new Oferece(
                ofereceJpa.getId(),
                PrestadorMapper.toPrestador(ofereceJpa.getPrestador()),
                ServicoMapper.toServico(ofereceJpa.getServico())
        );
    }
}
