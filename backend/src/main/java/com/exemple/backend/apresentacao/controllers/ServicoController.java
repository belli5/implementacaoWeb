package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.services.ServicoService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<Servico> save(@RequestBody Servico servico) {
        Servico novoServico = servicoService.save(servico);
        return ResponseEntity.ok(novoServico);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> findAll() {
        return ResponseEntity.ok(servicoService.findAll());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Servico>> findByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(servicoService.findByCategoria(categoria));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Servico> findByNome(@PathVariable String nome) {
        return servicoService.findByNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nome/{nome}")
    public ResponseEntity<String> deleteByNome(@PathVariable String nome) {
        boolean deleted = servicoService.deleteByNome(nome);
        if (deleted) {
            return ResponseEntity.ok("Serviço '" + nome + "' deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Serviço '" + nome + "' não encontrado.");
        }
    }
}
