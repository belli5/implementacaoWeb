package com.exemple.backend.apresentacao.controllers;


import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable int id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.findAll();
    }


    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente novoCliente) {
        Cliente criado = clienteService.create(novoCliente);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id,
                                             @RequestBody Cliente clienteAtualizado) {
        clienteAtualizado.setId(id);
        Cliente atualizado = clienteService.update(clienteAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> quemSouEu(Authentication authentication) {
        return ResponseEntity.ok(Map.of("mensagem", "Usuário autenticado: " + authentication.getName()));
    }


}
