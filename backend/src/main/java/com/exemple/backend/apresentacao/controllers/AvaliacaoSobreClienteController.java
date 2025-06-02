package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.services.AvaliacaoSobreClienteService;
import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.PrestadorService;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Cliente;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacaoSobreCliente")
public class AvaliacaoSobreClienteController {

    @Autowired
    private final AvaliacaoSobreClienteService service;
    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private ClienteService clienteService;

    public AvaliacaoSobreClienteController(AvaliacaoSobreClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoSobreCliente>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoSobreCliente> findById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoSobreCliente>> findByClienteId(@PathVariable int clienteId) {
        return ResponseEntity.ok(service.findByClienteId(clienteId));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoSobreCliente> save(@RequestBody AvaliacaoSobreCliente avaliacaoSobreCliente) {

        Optional<Prestador> prestador = prestadorService.findById(avaliacaoSobreCliente.getPrestador().getId());
        if (prestador.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteService.findById(avaliacaoSobreCliente.getCliente().getId());
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AvaliacaoSobreCliente novaAvaliacaoSobreCliente = new AvaliacaoSobreCliente(
                avaliacaoSobreCliente.getId(),
                prestador.get(),
                avaliacaoSobreCliente.getComentario(),
                avaliacaoSobreCliente.getNota(),
                cliente.get()
        );

        AvaliacaoSobreCliente salvo = service.save(novaAvaliacaoSobreCliente);
        return ResponseEntity.ok(salvo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}