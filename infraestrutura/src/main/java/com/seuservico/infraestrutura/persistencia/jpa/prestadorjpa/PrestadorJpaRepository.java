package com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestadorJpaRepository extends JpaRepository<PrestadorJpa, Integer> {

    // Buscar prestadores que oferecem determinado serviço (pela categoria do serviço)
    List<PrestadorJpa> findByServicos_Categoria(String categoria);
}