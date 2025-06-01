package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Oferece;

import java.util.List;
import java.util.Optional;

public interface OfereceRepository {
    // Precisa?????
    public Optional<Oferece> findById(int id);
    public List<Oferece> findAll();
    // -----------
    public List<Oferece> findByPrestadorId(int prestador_Id);
    public List<Oferece> findByServicoNome(String servico_Nome);
    public Oferece save(Oferece oferece);
    public void delete(int id);
}
