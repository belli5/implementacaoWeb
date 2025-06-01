package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoJpa, Integer> {
    List<PedidoJpa> findByPrestadorId(int prestador_Id);
    List<PedidoJpa> findByClienteId(int cliente_Id);
}
