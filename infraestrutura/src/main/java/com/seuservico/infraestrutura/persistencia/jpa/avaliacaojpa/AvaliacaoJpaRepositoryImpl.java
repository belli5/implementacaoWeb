package com.seuservico.infraestrutura.persistencia.jpa.avaliacaojpa;

import com.exemple.implementacaoweb2.avaliacao.Avaliacao;
import com.exemple.implementacaoweb2.avaliacao.AvaliacaoRepository;
import com.seuservico.infraestrutura.persistencia.jpa.MapperGeral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AvaliacaoJpaRepositoryImpl implements AvaliacaoRepository {

    @Autowired
    private AvaliacaoJpaRepository avaliacaoJpaRepository;

    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        AvaliacaoJpa avaliacaoJpa = MapperGeral.toAvaliacaoJpa(avaliacao);
        AvaliacaoJpa saved = avaliacaoJpaRepository.save(avaliacaoJpa);
        return MapperGeral.toAvaliacao(saved);
    }

    @Override
    public void delete(int id) {
        avaliacaoJpaRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        AvaliacaoJpa avaliacaoJpa = avaliacaoJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        // Exemplo de atualização (simples)
        avaliacaoJpa.setNota(5.0f); // Atualiza a nota para exemplo

        avaliacaoJpaRepository.save(avaliacaoJpa);
    }

    @Override
    public List<Avaliacao> findByPrestadorId(int prestadorId) {
        return avaliacaoJpaRepository.findByPrestadorId(prestadorId)
                .stream()
                .map(MapperGeral::toAvaliacao)
                .collect(Collectors.toList());
    }
}
