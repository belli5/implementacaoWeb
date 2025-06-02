package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;

import static org.springframework.util.Assert.notNull;

public class FavoritadoMapper {

    public static FavoritadoJpa toFavoritadoJpa(Favoritado favoritado) {
        notNull(favoritado, "Favoritado não pode ser nulo");

        FavoritadoJpa jpa = new FavoritadoJpa();
        jpa.setId(favoritado.getId());
        if (favoritado.getPrestador() != null) {
            jpa.setPrestador(PrestadorMapper.toPrestadorJpa(favoritado.getPrestador()));
        } else {
            jpa.setPrestador(null);
        }
        if (favoritado.getCliente() != null) {
            jpa.setCliente(ClienteMapper.toClienteJpa(favoritado.getCliente()));
        } else {
            jpa.setCliente(null);
        }
        return jpa;
    }


    public static Favoritado toFavoritado(FavoritadoJpa favoritadoJpa) {
        notNull(favoritadoJpa, "Favoritado não pode ser nulo");

        return new Favoritado(
                favoritadoJpa.getId(),
                favoritadoJpa.getCliente() != null ? ClienteMapper.toCliente(favoritadoJpa.getCliente()) : null,
                favoritadoJpa.getPrestador() != null ? PrestadorMapper.toPrestador(favoritadoJpa.getPrestador()) : null
        );
    }


}
