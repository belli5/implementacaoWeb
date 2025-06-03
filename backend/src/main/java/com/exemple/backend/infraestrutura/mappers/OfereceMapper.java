package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;

import static org.springframework.util.Assert.notNull;

public class OfereceMapper {

    public static OfereceJpa toOfereceJpa(Oferece oferece) {
        notNull(oferece, "Oferece não pode ser nulo");

        OfereceJpa jpa = new OfereceJpa();
        jpa.setId(oferece.getId());

        if(oferece.getPrestador() != null){
            jpa.setPrestador(PrestadorMapper.toPrestadorJpa(oferece.getPrestador()));
        } else {
            jpa.setPrestador(null);
        }
        if(oferece.getServico() != null){
            jpa.setServico(ServicoMapper.toServicoJpa(oferece.getServico()));
        } else {
            jpa.setServico(null);
        }

        return jpa;
    }

    public static Oferece toOferece(OfereceJpa ofereceJpa) {
        notNull(ofereceJpa, "OfereceJpa não pode ser nulo");

        return new Oferece(
                ofereceJpa.getId(),
                ofereceJpa.getPrestador() != null ? PrestadorMapper.toPrestador(ofereceJpa.getPrestador()) : null,
                ofereceJpa.getServico() != null ? ServicoMapper.toServico(ofereceJpa.getServico()) : null
        );
    }
}
