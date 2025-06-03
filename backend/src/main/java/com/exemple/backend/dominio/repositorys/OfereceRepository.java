package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;

import java.util.List;
import java.util.Optional;

public interface OfereceRepository {
    // Precisa?????
    public Optional<Oferece> findById(int id);
    public List<Oferece> findAll();
    // -----------
    public List<Servico> findByPrestadorId(int prestador_Id);
    public List<Prestador> findByServicoNome(String servico_Nome);
    public Oferece save(Oferece oferece);
    public void delete(int id);
}
