package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritadoJpaRepository extends JpaRepository<FavoritadoJpa, Integer> {

    List<FavoritadoJpa> findByPrestador_Id(Integer prestador_Id);
    List<FavoritadoJpa> findByCliente_Id(Integer cliente_Id);
}
