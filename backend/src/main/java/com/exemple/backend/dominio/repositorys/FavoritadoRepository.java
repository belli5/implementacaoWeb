package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Favoritado;

import java.util.List;
import java.util.Optional;

public interface FavoritadoRepository {
    // Precisa de um metodo que encontre somente 1?????
    public Optional<Favoritado> findById(int id);
    public List<Favoritado> findAll();
    // ------------------------------------------------
    public List<Favoritado> findByPrestadorId(int prestador_Id);
    public List<Favoritado> findbyClienteId(int cliente_Id);
    public Favoritado save(Favoritado favoritado);
    public void delete(int id);


}
