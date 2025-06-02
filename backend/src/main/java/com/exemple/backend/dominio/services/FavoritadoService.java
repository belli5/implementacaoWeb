package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritadoService {

    private final FavoritadoRepository favoritadoRepository;

    public FavoritadoService(FavoritadoRepository favoritadoRepository) {
        this.favoritadoRepository = favoritadoRepository;
    }

    public Optional<Favoritado> buscarPorId(int id) {
        return favoritadoRepository.findById(id);
    }


    public List<Favoritado> listarTodos() {
        return favoritadoRepository.findAll();
    }

    public List<Prestador> listarFavoritosDoCliente(int clienteId) {
        return favoritadoRepository.findPrestadoresFavoritadosByClienteId(clienteId);
    }

    public List<Cliente> listarClientesQueFavoritaramPrestador(int prestadorId) {
        return favoritadoRepository.findClientesQueFavoritaramPrestadorByPrestadorId(prestadorId);
    }

    public Favoritado favoritar(Favoritado favoritado) {
        return favoritadoRepository.save(favoritado);
    }

    public void desfavoritar(int id) {
        favoritadoRepository.delete(id);
    }
}
