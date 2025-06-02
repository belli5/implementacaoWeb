package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.services.AvaliacaoSobrePrestadorService;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacaoSobrePrestador")
public class AvaliacaoSobrePrestadorController {
    @Autowired
    private AvaliacaoSobrePrestadorService avaliacaoSobrePrestadorService;
    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private ClienteService clienteService;


    @PostMapping("/novaAvaliacaoSobrePrestador")
    public ResponseEntity<AvaliacaoSobrePrestador> criarAvaliacaoSobrePrestador(@RequestBody AvaliacaoSobrePrestador avaliacaoSobrePrestador) {

        Optional<Prestador> prestador = prestadorService.findById(avaliacaoSobrePrestador.getPrestador().getId());
        if (prestador.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Cliente> cliente = clienteService.findById(avaliacaoSobrePrestador.getCliente().getId());
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AvaliacaoSobrePrestador novaAvaliacaoSobrePrestador = new AvaliacaoSobrePrestador(
                cliente.get(),
                avaliacaoSobrePrestador.getComentario(),
                avaliacaoSobrePrestador.getNota(),
                prestador.get()
        );
        AvaliacaoSobrePrestador salvoAvaliacaoSobrePrestador = avaliacaoSobrePrestadorService.save(novaAvaliacaoSobrePrestador);
        return ResponseEntity.ok(salvoAvaliacaoSobrePrestador);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoSobrePrestador> encontrarAvaliacaoSobrePrestador(@PathVariable int id){
        Optional<AvaliacaoSobrePrestador> avaliacao = avaliacaoSobrePrestadorService.findById(id);
        return avaliacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todas_as_avaliacoes")
    public ResponseEntity<List<AvaliacaoSobrePrestador>> listarTodasAvaliacoesSobrePrestador(){
        List<AvaliacaoSobrePrestador> avaliacoes = avaliacaoSobrePrestadorService.findAll();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/avaliacoes_por_prestador/{prestador_id}")
    public ResponseEntity<List<AvaliacaoSobrePrestador>> listarAvaliacoesPorPrestador(@PathVariable int prestador_id){

        Optional<Prestador> prestador = prestadorService.findById(prestador_id);
        if(prestador.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<AvaliacaoSobrePrestador> avaliacoes = avaliacaoSobrePrestadorService.findByPrestadorId(prestador_id);
        return ResponseEntity.ok(avaliacoes);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable int id){
        Optional<AvaliacaoSobrePrestador> avaliacao_existente = avaliacaoSobrePrestadorService.findById(id);

        if(avaliacao_existente.isPresent()){
            avaliacaoSobrePrestadorService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }
}

