package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoJpaRepository extends JpaRepository<ServicoJpa, String> {
    Optional<ServicoJpa> findByNome(String nome);
    List<ServicoJpa> findByCategoria(String categoria);
    void deleteByNome(String nome);
}
