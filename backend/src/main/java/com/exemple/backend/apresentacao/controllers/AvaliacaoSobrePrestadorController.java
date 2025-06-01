package com.exemple.backend.apresentacao.controllers;


import com.exemple.backend.dominio.services.AvaliacaoSobrePrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacaoSobrePrestador")
public class AvaliacaoSobrePrestadorController {
    @Autowired
    private AvaliacaoSobrePrestadorService avaliacaoSobrePrestadorService;
}
