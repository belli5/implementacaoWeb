package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.services.OfereceService;
import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.services.PrestadorService;
import com.exemple.backend.dominio.services.ServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oferece")
public class OfereceController {

    private final OfereceService ofereceService;
    private final PrestadorService prestadorService;
    private final ServicoService servicoService;


    public OfereceController(OfereceService ofereceService, PrestadorService prestadorService, ServicoService servicoService) {
        this.ofereceService = ofereceService;
        this.prestadorService = prestadorService;
        this.servicoService = servicoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferece> buscarPorId(@PathVariable int id) {
        Optional<Oferece> oferece = ofereceService.buscarPorId(id);
        return oferece.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/servicosPorPrestador/{prestadorId}")
    public ResponseEntity<List<Servico>> buscarPorPrestador(@PathVariable int prestadorId) {
        Optional<Prestador> prestador = prestadorService.findById(prestadorId);
        if(prestador.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Servico> servicos = ofereceService.buscarPorPrestadorId(prestadorId);
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/prestadoresPorServico/{nome}")
    public ResponseEntity<List<Prestador>> buscarPorServico(@PathVariable String nome) {
        Optional<Servico> servico = servicoService.findByNome(nome);
        if(servico.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Prestador> prestadores = ofereceService.buscarPorServicoNome(nome);
        return ResponseEntity.ok(prestadores);
    }

    @PostMapping()
    public ResponseEntity<Oferece> criar(@RequestBody Oferece oferece) {

        Optional<Prestador> prestador = prestadorService.findById(oferece.getPrestador().getId());
        if(prestador.isEmpty()){
            return  ResponseEntity.notFound().build();
        }

        Optional<Servico> servico = servicoService.findByNome(oferece.getServico().getNome());
        if(servico.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Oferece novoOferece = new Oferece(
                prestador.get(),
                servico.get()
        );

        Oferece salvoOferece = ofereceService.salvar(novoOferece);
        return ResponseEntity.ok(salvoOferece);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<Oferece> oferece_existente = ofereceService.buscarPorId(id);

        if(oferece_existente.isPresent()){
            ofereceService.deletar(id);
            return ResponseEntity.noContent().build();
        }

        else{
            return ResponseEntity.notFound().build();
        }
    }
}
