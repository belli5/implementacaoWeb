package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;

import static org.springframework.util.Assert.notNull;

public class FavoritadoMapper {

    public static FavoritadoJpa toFavoritadoJpa(Favoritado favoritado) {
        notNull(favoritado, "Favoritado não pode ser nulo");

        return new FavoritadoJpa(
                favoritado.getId(),
                PrestadorMapper.toPrestadorJpa(favoritado.getPrestador()),
                ClienteMapper.toClienteJpa(favoritado.getCliente())
        );
    }

    public static Favoritado toFavoritado(FavoritadoJpa favoritadoJpa) {
        notNull(favoritadoJpa, "FavoritadoJpa não pode ser nulo");

        return new Favoritado(
                favoritadoJpa.getId(),
                ClienteMapper.toCliente(favoritadoJpa.getCliente()),
                PrestadorMapper.toPrestador(favoritadoJpa.getPrestador())
        );
    }
}
