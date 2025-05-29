package com.exemple.apresentacaoback.apresentacao;

import com.exemple.implementacaoweb2.avaliacao.Avaliacao;
import com.exemple.implementacaoweb2.avaliacao.AvaliacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> cadastrar(@RequestBody Avaliacao avaliacao) {
        Avaliacao nova = avaliacaoService.cadastrarAvaliacao(avaliacao);
        return ResponseEntity.ok(nova);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        avaliacaoService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody Avaliacao novosDados) {
        try {
            Avaliacao atualizada = avaliacaoService.atualizarAvaliacao(id, novosDados);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<Avaliacao>> buscarPorPrestador(@PathVariable int prestadorId) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesDoPrestador(prestadorId);
        return ResponseEntity.ok(avaliacoes);
    }
}
