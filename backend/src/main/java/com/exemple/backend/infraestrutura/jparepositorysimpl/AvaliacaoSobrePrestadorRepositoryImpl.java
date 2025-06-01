package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.repositorys.AvaliacaoSobrePrestadorRepository;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.AvaliacaoSobrePrestadorJpaRepository;
import com.exemple.backend.infraestrutura.mappers.AvaliacaoSobrePrestadorMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AvaliacaoSobrePrestadorRepositoryImpl implements AvaliacaoSobrePrestadorRepository {

    private final AvaliacaoSobrePrestadorJpaRepository avaliacaoSobrePrestadorJpaRepository;

    public AvaliacaoSobrePrestadorRepositoryImpl(
            AvaliacaoSobrePrestadorJpaRepository avaliacaoSobrePrestadorJpaRepository
    ) {
        this.avaliacaoSobrePrestadorJpaRepository = avaliacaoSobrePrestadorJpaRepository;
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
        AvaliacaoSobrePrestadorJpa entity = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(avaliacao);
        AvaliacaoSobrePrestadorJpa saved = avaliacaoSobrePrestadorJpaRepository.save(entity);
        return AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(saved);
    }

    @Override
    public void delete(int id) {
        avaliacaoSobrePrestadorJpaRepository.deleteById(id);
    }
}
