package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoSobrePrestadorRepository {
    //Precisa???????
    public Optional<AvaliacaoSobrePrestador> findById(int id);
    public List<AvaliacaoSobrePrestador> findAll();
    //--------------
    public List<AvaliacaoSobrePrestador> findByPrestadorId(int prestadorId);
    public AvaliacaoSobrePrestador save(AvaliacaoSobrePrestador avaliacaoSobrePrestador);
    public void delete(int id);
}
