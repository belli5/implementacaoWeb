package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface FavoritadoRepository {
    // Precisa de um metodo que encontre somente 1?????
    public Optional<Favoritado> findById(int id);
    public List<Favoritado> findAll();
    // ------------------------------------------------
    public List<Cliente> findClientesQueFavoritaramPrestadorByPrestadorId(int prestadorId);
    public List<Prestador> findPrestadoresFavoritadosByClienteId(int clienteId);
    public Favoritado save(Favoritado favoritado);
    public void delete(int id);


}
