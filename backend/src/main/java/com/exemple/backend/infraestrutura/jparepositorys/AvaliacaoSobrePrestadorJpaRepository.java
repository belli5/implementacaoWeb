package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoSobrePrestadorJpaRepository extends JpaRepository<AvaliacaoSobrePrestadorJpa, Integer> {

    List<AvaliacaoSobrePrestadorJpa> findByPrestadorId(Integer prestador_Id);
}
