package com.exemple.apresentacaoback.apresentacao;

import com.exemple.implementacaoweb2.cliente.Cliente;
import com.exemple.implementacaoweb2.cliente.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Cadastrar cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);
        return ResponseEntity.ok(novoCliente);
    }

    // Deletar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable int id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar cliente (forma atual: apenas atualiza telefone como exemplo)
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(@PathVariable int id) {
        clienteService.atualizarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar clientes por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(clienteService.buscarPorNome(nome));
    }

    // Buscar clientes por email
    @GetMapping("/buscar/email")
    public ResponseEntity<List<Cliente>> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(clienteService.buscarPorEmail(email));
    }

    // Buscar clientes por telefone
    @GetMapping("/buscar/telefone")
    public ResponseEntity<List<Cliente>> buscarPorTelefone(@RequestParam String telefone) {
        return ResponseEntity.ok(clienteService.buscarPorTelefone(telefone));
    }

    // Buscar clientes que favoritaram um prestador
    @GetMapping("/buscar/prestador-favorito")
    public ResponseEntity<List<Cliente>> buscarPorPrestadorFavorito(@RequestParam int prestadorId) {
        return ResponseEntity.ok(clienteService.buscarPorPrestadorFavorito(prestadorId));
    }
}
