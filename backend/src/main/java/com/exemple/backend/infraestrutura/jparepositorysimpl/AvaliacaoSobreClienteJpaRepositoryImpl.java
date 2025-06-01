package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobreClienteRepository;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.AvaliacaoSobreClienteJpaRepository;
import com.exemple.backend.infraestrutura.mappers.AvaliacaoSobreClienteMapper;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AvaliacaoSobreClienteJpaRepositoryImpl implements AvaliacaoSobreClienteRepository {

    private final AvaliacaoSobreClienteJpaRepository avaliacaoSobreClienteJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final PrestadorJpaRepository prestadorJpaRepository;

    public AvaliacaoSobreClienteJpaRepositoryImpl(
            AvaliacaoSobreClienteJpaRepository avaliacaoSobreClienteJpaRepository,
            ClienteJpaRepository clienteJpaRepository,
            PrestadorJpaRepository prestadorJpaRepository
    ) {
        this.avaliacaoSobreClienteJpaRepository = avaliacaoSobreClienteJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
        this.prestadorJpaRepository = prestadorJpaRepository;
    }

    @Override
    public Optional<AvaliacaoSobreCliente> findById(int id) {
        return avaliacaoSobreClienteJpaRepository.findById(id)
                .map(AvaliacaoSobreClienteMapper::toAvaliacaoSobreCliente);
    }

    @Override
    public List<AvaliacaoSobreCliente> findAll() {
        return avaliacaoSobreClienteJpaRepository.findAll().stream()
                .map(AvaliacaoSobreClienteMapper::toAvaliacaoSobreCliente)
                .toList();
    }

    @Override
    public List<AvaliacaoSobreCliente> findByClienteId(int cliente_Id) {
        return avaliacaoSobreClienteJpaRepository.findByClienteId(cliente_Id).stream()
                .map(AvaliacaoSobreClienteMapper::toAvaliacaoSobreCliente)
                .toList();
    }

    @Override
    public AvaliacaoSobreCliente save(AvaliacaoSobreCliente avaliacao) {
        // Garante que as entidades JPA associadas existem antes de salvar
        ClienteJpa clienteJpa = clienteJpaRepository.findById(avaliacao.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(avaliacao.getPrestador().getId())
                .orElseThrow(() -> new IllegalArgumentException("Prestador não encontrado"));

        AvaliacaoSobreClienteJpa entity = AvaliacaoSobreClienteMapper.toAvaliacaoSobreClienteJpa(avaliacao);
        entity.setCliente(clienteJpa);
        entity.setPrestador(prestadorJpa);

        AvaliacaoSobreClienteJpa saved = avaliacaoSobreClienteJpaRepository.save(entity);
        return AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(saved);
    }

    @Override
    public void delete(int id) {
        avaliacaoSobreClienteJpaRepository.deleteById(id);
    }
}
