package com.exemple.apresentacaoback.apresentacao;

import com.exemple.implementacaoweb2.prestador.Prestador;
import com.exemple.implementacaoweb2.prestador.PrestadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @PostMapping
    public ResponseEntity<Prestador> cadastrarPrestador(@RequestBody Prestador prestador) {
        Prestador novo = prestadorService.cadastrarPrestador(prestador);
        return ResponseEntity.ok(novo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPrestador(@PathVariable int id) {
        prestadorService.deletarPrestador(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> atualizarPrestador(@RequestBody Prestador prestador) {
        try {
            prestadorService.atualizarPrestador(prestador);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar prestador: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestador> buscarPorId(@PathVariable int id) {
        return prestadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-por-servico")
    public ResponseEntity<List<Prestador>> buscarPorServico(@RequestParam String servico) {
        return ResponseEntity.ok(prestadorService.buscarPorServico(servico));
    }

    @GetMapping
    public ResponseEntity<List<Prestador>> listarTodos() {
        return ResponseEntity.ok(prestadorService.listarTodos());
    }
}
