package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobrePrestadorRepository;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.AvaliacaoSobrePrestadorJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.mappers.AvaliacaoSobrePrestadorMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AvaliacaoSobrePrestadorJpaRepositoryImpl implements AvaliacaoSobrePrestadorRepository {

    private final AvaliacaoSobrePrestadorJpaRepository avaliacaoSobrePrestadorJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final PrestadorJpaRepository prestadorJpaRepository;

    public AvaliacaoSobrePrestadorJpaRepositoryImpl(
            AvaliacaoSobrePrestadorJpaRepository avaliacaoSobrePrestadorJpaRepository,
            ClienteJpaRepository clienteJpaRepository,
            PrestadorJpaRepository prestadorJpaRepository
    ) {
        this.avaliacaoSobrePrestadorJpaRepository = avaliacaoSobrePrestadorJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
        this.prestadorJpaRepository = prestadorJpaRepository;
    }

    @Override
    public Optional<AvaliacaoSobrePrestador> findById(int id) {
        return avaliacaoSobrePrestadorJpaRepository.findById(id)
                .map(AvaliacaoSobrePrestadorMapper::toAvaliacaoSobrePrestador);
    }

    @Override
    public List<AvaliacaoSobrePrestador> findAll() {
        return avaliacaoSobrePrestadorJpaRepository.findAll().stream()
                .map(AvaliacaoSobrePrestadorMapper::toAvaliacaoSobrePrestador)
                .toList();
    }

    @Override
    public List<AvaliacaoSobrePrestador> findByPrestadorId(int prestador_Id) {
        return avaliacaoSobrePrestadorJpaRepository.findByPrestadorId(prestador_Id).stream()
                .map(AvaliacaoSobrePrestadorMapper::toAvaliacaoSobrePrestador)
                .toList();
    }

    @Override
    public AvaliacaoSobrePrestador save(AvaliacaoSobrePrestador avaliacao) {
        // Garante que as entidades JPA associadas existem antes de salvar
        ClienteJpa clienteJpa = clienteJpaRepository.findById(avaliacao.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(avaliacao.getPrestador().getId())
                .orElseThrow(() -> new IllegalArgumentException("Prestador não encontrado"));

        // Converte para entidade JPA e garante as referências corretas
        AvaliacaoSobrePrestadorJpa entity = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(avaliacao);
        entity.setCliente(clienteJpa);
        entity.setPrestador(prestadorJpa);

        AvaliacaoSobrePrestadorJpa saved = avaliacaoSobrePrestadorJpaRepository.save(entity);
        return AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(saved);
    }

    @Override
    public void delete(int id) {
        avaliacaoSobrePrestadorJpaRepository.deleteById(id);
    }
}
