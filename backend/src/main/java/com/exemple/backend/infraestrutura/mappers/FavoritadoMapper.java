package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;

import static org.springframework.util.Assert.notNull;

public class FavoritadoMapper {

    public static FavoritadoJpa toFavoritadoJpa(Favoritado favoritado) {
        notNull(favoritado, "Favoritado não pode ser nulo");

        FavoritadoJpa jpa = new FavoritadoJpa();
        jpa.setId(favoritado.getId());
        jpa.setPrestador(null);
        jpa.setCliente(null);
        return jpa;
    }

    public static Favoritado toFavoritado(FavoritadoJpa favoritadoJpa) {
        notNull(favoritadoJpa, "FavoritadoJpa não pode ser nulo");

        return new Favoritado(
                favoritadoJpa.getId(),
                null,
                null
        );
    }
}
