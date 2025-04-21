package org.example.persistence.repository.servicos;

import org.example.model.servicos.Servico;
import java.util.List;

public interface ServicoRepository {
    Servico save(Servico servico);
    void delete(int id);
    void update(int id);
    List<Servico> buscarPorBairro(String bairro);
}
