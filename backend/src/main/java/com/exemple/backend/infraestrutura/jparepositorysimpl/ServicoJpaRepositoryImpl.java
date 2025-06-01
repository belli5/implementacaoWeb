package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.repositorys.ServicoRepository;
import com.exemple.backend.infraestrutura.jparepositorys.ServicoJpaRepository;
import com.exemple.backend.infraestrutura.mappers.ServicoMapper;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.springframework.stereotype.Repository;
import static org.springframework.util.Assert.notNull;

import java.util.List;
import java.util.Optional;

@Repository
public class ServicoJpaRepositoryImpl implements ServicoRepository {

    private final ServicoJpaRepository servicoJpaRepository;

    public ServicoJpaRepositoryImpl(ServicoJpaRepository servicoJpaRepository) {
        this.servicoJpaRepository = servicoJpaRepository;
    }

    @Override
    public Optional<Servico> findByNome(String nome) {
        notNull(nome, "Nome não deve ser nulo");

        return servicoJpaRepository.findByNome(nome)
                .map(ServicoMapper::toServico);
    }

    @Override
    public List<Servico> findByCategoria(String categoria) {
        notNull(categoria, "Categoria não deve ser nula");

        return servicoJpaRepository.findByCategoria(categoria)
                .stream()
                .map(ServicoMapper::toServico)
                .toList();
    }

    @Override
    public List<Servico> findAll() {
        return servicoJpaRepository.findAll()
                .stream()
                .map(ServicoMapper::toServico)
                .toList();
    }

    @Override
    public Servico save(Servico servico) {
        notNull(servico, "Servico não deve ser nulo");

        ServicoJpa servicoJpa = ServicoMapper.toServicoJpa(servico);
        return ServicoMapper.toServico(servicoJpaRepository.save(servicoJpa));
    }

    @Override
    public void deleteByNome(String nome) {
        notNull(nome, "Nome não deve ser nulo");

        servicoJpaRepository.deleteByNome(nome);
    }
}
