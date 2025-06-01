package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoSobreClienteJpaRepository extends JpaRepository<AvaliacaoSobreClienteJpa, Integer> {

    List<AvaliacaoSobreClienteJpa> findByClienteId(Integer cliente_Id);
}
