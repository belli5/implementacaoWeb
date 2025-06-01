package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfereceJpaRepository extends JpaRepository<OfereceJpa, Integer> {
    List<OfereceJpa> findByPrestadorId(int prestador_Id);
    List<OfereceJpa> findByServicoNome(String servico_Nome);
}
