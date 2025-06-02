package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.services.OfereceService;
import com.exemple.backend.dominio.models.Oferece;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oferece")
//@CrossOrigin(origins = "*")
public class OfereceController {

    private final OfereceService ofereceService;

    public OfereceController(OfereceService ofereceService) {
        this.ofereceService = ofereceService;
    }

    @GetMapping
    public List<Oferece> listarTodos() {
        return ofereceService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferece> buscarPorId(@PathVariable int id) {
        return ofereceService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Oferece> buscarPorPrestador(@PathVariable int prestadorId) {
        return ofereceService.buscarPorPrestadorId(prestadorId);
    }

    @GetMapping("/servico/{nome}")
    public List<Oferece> buscarPorServico(@PathVariable String nome) {
        return ofereceService.buscarPorServicoNome(nome);
    }

    @PostMapping
    public ResponseEntity<Oferece> criar(@RequestBody Oferece oferece) {
        Oferece criado = ofereceService.salvar(oferece);
        return ResponseEntity.ok(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        ofereceService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
