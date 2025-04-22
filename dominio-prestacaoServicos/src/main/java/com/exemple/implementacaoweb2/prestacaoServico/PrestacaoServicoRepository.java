package com.exemple.implementacaoweb2.prestacaoServico;

import java.util.ArrayList;
import java.util.List;

public interface PrestacaoServicoRepository {
    PrestacaoServico save(PrestacaoServico prestacaoServico);
    void delete(int id);
    void update(int id);
    List<PrestacaoServico> buscarPorBairro(String bairro);

    List<PrestacaoServico> findAll();
}
