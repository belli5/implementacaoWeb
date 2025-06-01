package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;

import static org.springframework.util.Assert.notNull;

public class OfereceMapper {

    public static OfereceJpa toOfereceJpa(Oferece oferece) {
        notNull(oferece, "Oferece não pode ser nulo");

        OfereceJpa jpa = new OfereceJpa();
        jpa.setId(oferece.getId());
        jpa.setPrestador(null);
        jpa.setServico(null);

        return jpa;
    }

    public static Oferece toOferece(OfereceJpa ofereceJpa) {
        notNull(ofereceJpa, "OfereceJpa não pode ser nulo");

        return new Oferece(
                ofereceJpa.getId(),
                null,
                null
        );
    }
}
