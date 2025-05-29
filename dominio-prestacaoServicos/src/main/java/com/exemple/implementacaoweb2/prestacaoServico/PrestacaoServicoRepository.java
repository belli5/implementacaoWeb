package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.List;
import java.util.Optional;

public interface PrestacaoServicoRepository {

    public PrestacaoServico save(PrestacaoServico prestacaoServico);

    public void delete(int id);

    public void update(int id);

    public Optional<PrestacaoServico> findById(int id);

    public List<PrestacaoServico> buscarPorBairro(String bairro);

    public List<PrestacaoServico> buscarPorCategoria(String categoria);

    public List<PrestacaoServico> buscarPorPrestadorId(int prestadorId);

    public List<PrestacaoServico> findAll();
}
