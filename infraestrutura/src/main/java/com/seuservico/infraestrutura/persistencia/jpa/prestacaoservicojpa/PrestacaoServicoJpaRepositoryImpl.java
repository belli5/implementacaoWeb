package com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa;

import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServicoRepository;
import com.seuservico.infraestrutura.persistencia.jpa.MapperGeral;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PrestacaoServicoJpaRepositoryImpl implements PrestacaoServicoRepository {

    @Autowired
    private PrestacaoServicoJpaRepository prestacaoServicoJpaRepository;

    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    @Override
    public PrestacaoServico save(PrestacaoServico servico) {
        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(servico.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        PrestacaoServicoJpa servicoJpa = MapperGeral.toPrestacaoServicoJpa(servico, prestadorJpa);

        PrestacaoServicoJpa saved = prestacaoServicoJpaRepository.save(servicoJpa);

        return MapperGeral.toPrestacaoServico(saved);
    }

    @Override
    public void delete(int id) {
        prestacaoServicoJpaRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        PrestacaoServicoJpa servicoJpa = prestacaoServicoJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        // Exemplo simples de alteração
        servicoJpa.setDescricao("Descrição atualizada");

        prestacaoServicoJpaRepository.save(servicoJpa);
    }

    @Override
    public Optional<PrestacaoServico> findById(int id) {
        return prestacaoServicoJpaRepository.findById(id)
                .map(MapperGeral::toPrestacaoServico);
    }

    @Override
    public List<PrestacaoServico> buscarPorBairro(String bairro) {
        return prestacaoServicoJpaRepository.findByBairro(bairro)
                .stream()
                .map(MapperGeral::toPrestacaoServico)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestacaoServico> buscarPorCategoria(String categoria) {
        return prestacaoServicoJpaRepository.findByCategoria(categoria)
                .stream()
                .map(MapperGeral::toPrestacaoServico)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestacaoServico> buscarPorPrestadorId(int prestadorId) {
        return prestacaoServicoJpaRepository.findByPrestador_Id(prestadorId)
                .stream()
                .map(MapperGeral::toPrestacaoServico)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestacaoServico> findAll() {
        return prestacaoServicoJpaRepository.findAll()
                .stream()
                .map(MapperGeral::toPrestacaoServico)
                .collect(Collectors.toList());
    }
}
