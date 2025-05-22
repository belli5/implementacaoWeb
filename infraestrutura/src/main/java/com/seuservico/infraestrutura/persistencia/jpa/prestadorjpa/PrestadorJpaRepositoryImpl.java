package com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa;

import com.exemple.implementacaoweb2.prestador.Prestador;
import com.exemple.implementacaoweb2.prestador.PrestadorRepository;
import com.seuservico.infraestrutura.persistencia.jpa.MapperGeral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PrestadorJpaRepositoryImpl implements PrestadorRepository {

    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    @Override
    public Prestador save(Prestador prestador) {
        PrestadorJpa prestadorJpa = MapperGeral.toPrestadorJpa(prestador);
        PrestadorJpa saved = prestadorJpaRepository.save(prestadorJpa);
        return MapperGeral.toPrestador(saved);
    }

    @Override
    public void delete(int id) {
        prestadorJpaRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        // Exemplo simples de atualização
        prestadorJpa.setTelefone("Novo telefone");

        prestadorJpaRepository.save(prestadorJpa);
    }

    @Override
    public Optional<Prestador> findById(int id) {
        return prestadorJpaRepository.findById(id)
                .map(MapperGeral::toPrestador);
    }

    @Override
    public List<Prestador> findByServico(String servico) {
        return prestadorJpaRepository.findByServicos_Categoria(servico)
                .stream()
                .map(MapperGeral::toPrestador)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestador> findAll() {
        return prestadorJpaRepository.findAll()
                .stream()
                .map(MapperGeral::toPrestador)
                .collect(Collectors.toList());
    }
}
