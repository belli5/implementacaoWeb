package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.FavoritadoService;
import com.exemple.backend.dominio.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class FavoritadoController {

    @Autowired
    private final FavoritadoService favoritadoService;

    @Autowired
    private final PrestadorService prestadorService;

    @Autowired
    private final ClienteService clienteService;


    public FavoritadoController(FavoritadoService favoritadoService, PrestadorService prestadorService, ClienteService clienteService) {
        this.favoritadoService = favoritadoService;
        this.prestadorService = prestadorService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Favoritado>> listarTodos() {
        List<Favoritado> favoritados = favoritadoService.listarTodos();

        return ResponseEntity.ok(favoritados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favoritado> buscarPorId(@PathVariable int id) {
        Optional<Favoritado> favorito = favoritadoService.buscarPorId(id);
        return favorito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("favoritosDoCliente/{cliente_id}")
    public ResponseEntity<List<Prestador>> listarFavoritosDoCliente(@PathVariable int cliente_id){

        Optional<Cliente> cliente = clienteService.findById(cliente_id);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();

        }

        List<Prestador> favoritados = favoritadoService.listarFavoritosDoCliente(cliente_id);
        return ResponseEntity.ok(favoritados);
    }

    @GetMapping("clientesQueFavoritaram/{prestador_id}")
    public ResponseEntity<List<Cliente>> listarClientesQueFavoritaramPrestador(@PathVariable int prestador_id){

        Optional<Prestador> prestador = prestadorService.findById(prestador_id);
        if(prestador.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Cliente> favoritaram = favoritadoService.listarClientesQueFavoritaramPrestador(prestador_id);
        return ResponseEntity.ok(favoritaram);
    }


    @PostMapping()
    public ResponseEntity<Favoritado> favoritar (@RequestBody Favoritado favoritado){

        Optional<Prestador> prestador = prestadorService.findById(favoritado.getPrestador().getId());
        if (prestador.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteService.findById(favoritado.getCliente().getId());
        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Favoritado novoFavorito = new Favoritado(
                cliente.get(),
                prestador.get()
        );
        Favoritado salvoFavoritado = favoritadoService.favoritar(novoFavorito);
        return ResponseEntity.ok(salvoFavoritado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desfavoritar(@PathVariable int id){
        Optional<Favoritado> favoritado_existente = favoritadoService.buscarPorId(id);

        if(favoritado_existente.isPresent()){
            favoritadoService.desfavoritar(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }



}







//
//    public Favoritado favoritar(Favoritado favoritado) {
//        return favoritadoRepository.save(favoritado);
//    }
