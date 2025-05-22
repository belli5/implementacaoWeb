package com.seuservico.infraestrutura.persistencia.jpa.clientejpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteJpa, Integer> {

    List<ClienteJpa> findByNome(String nome);

    List<ClienteJpa> findByEmail(String email);

    List<ClienteJpa> findByTelefone(String telefone);

    List<ClienteJpa> findByPrestadoresFavoritos_Id(int prestadorId);
}
