package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.List;
import java.util.Optional;

public interface PrestacaoServicoRepository {

    PrestacaoServico save(PrestacaoServico prestacaoServico);

    void delete(int id);

    void update(int id);

    Optional<PrestacaoServico> findById(int id);

    List<PrestacaoServico> buscarPorBairro(String bairro);

    List<PrestacaoServico> buscarPorCategoria(String categoria);

    List<PrestacaoServico> buscarPorPrestadorId(int prestadorId);

    List<PrestacaoServico> findAll();
}
