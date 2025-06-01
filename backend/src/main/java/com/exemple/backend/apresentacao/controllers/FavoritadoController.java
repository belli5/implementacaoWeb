package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class FavoritadoController {

    private final FavoritadoRepository favoritadoRepository;

    public FavoritadoController(FavoritadoRepository favoritadoRepository) {
        this.favoritadoRepository = favoritadoRepository;
    }

    @GetMapping
    public List<Favoritado> listarTodos() {
        return favoritadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favoritado> buscarPorId(@PathVariable int id) {
        Optional<Favoritado> favorito = favoritadoRepository.findById(id);
        return favorito.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Favoritado> favoritar(@RequestBody Favoritado favoritado) {
        Favoritado salvo = favoritadoRepository.save(favoritado);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desfavoritar(@PathVariable int id) {
        Optional<Favoritado> existente = favoritadoRepository.findById(id);
        if (existente.isPresent()) {
            favoritadoRepository.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Prestador> listarFavoritosDoCliente(@PathVariable int clienteId) {
        return favoritadoRepository.findPrestadoresFavoritadosByClienteId(clienteId);
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Cliente> listarClientesQueFavoritaramPrestador(@PathVariable int prestadorId) {
        return favoritadoRepository.findClientesQueFavoritaramPrestadorByPrestadorId(prestadorId);
    }
}
