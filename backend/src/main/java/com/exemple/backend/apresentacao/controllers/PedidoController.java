package com.exemple.backend.apresentacao.controllers;


import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable int id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<Pedido>> buscarPorPrestador(@PathVariable int prestadorId) {
        return ResponseEntity.ok(pedidoService.buscarPorPrestador(prestadorId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable int clienteId) {
        return ResponseEntity.ok(pedidoService.buscarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Pedido criado = pedidoService.salvar(pedido);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable int id, @RequestBody Pedido pedido) {
        pedido.setId(id); // garantir que está atualizando o pedido certo
        Pedido atualizado = pedidoService.atualizar(pedido);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
