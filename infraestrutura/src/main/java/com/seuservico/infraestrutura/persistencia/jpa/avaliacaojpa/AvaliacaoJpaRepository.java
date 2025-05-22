package com.seuservico.infraestrutura.persistencia.jpa.avaliacaojpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoJpaRepository extends JpaRepository<AvaliacaoJpa, Integer> {

    List<AvaliacaoJpa> findByPrestadorId(int prestadorId);

}
