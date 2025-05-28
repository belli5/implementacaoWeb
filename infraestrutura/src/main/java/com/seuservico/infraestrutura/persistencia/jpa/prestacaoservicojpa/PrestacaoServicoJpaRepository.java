package com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestacaoServicoJpaRepository extends JpaRepository<PrestacaoServicoJpa, Integer> {

    List<PrestacaoServicoJpa> findByBairro(String bairro);

    List<PrestacaoServicoJpa> findByCategoria(String categoria);

    List<PrestacaoServicoJpa> findByPrestador_Id(int prestadorId);
}