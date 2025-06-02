package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/prestador")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    @GetMapping("/{id}")
    public ResponseEntity<Prestador> findById(@PathVariable int id){
        Optional<Prestador> prestador = prestadorService.findById(id);
        return prestador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Prestador>> findAll() {
        return ResponseEntity.ok(prestadorService.findAll());
    }

    @PostMapping
    public ResponseEntity<Prestador> save(@RequestBody Prestador prestador) {
        Prestador salvo = prestadorService.save(prestador);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Prestador> update(@PathVariable int id, @RequestBody Prestador prestador) {
        Optional<Prestador> existente = prestadorService.findById(id);
        if (existente.isPresent()) {
            prestador.setId(id);
            Prestador atualizado = prestadorService.update(prestador);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Prestador> existente = prestadorService.findById(id);
        if (existente.isPresent()) {
            prestadorService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
