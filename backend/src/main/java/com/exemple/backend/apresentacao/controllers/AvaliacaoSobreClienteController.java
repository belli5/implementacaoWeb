package com.exemple.backend.apresentacao.controllers;
import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.services.AvaliacaoSobreClienteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // Respostas em http
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping('/clienteAvaliacao') // Precisa ajeitar esse map ainda
public class AvaliacaoSobreClienteController {
    private final AvaliacaoSobreClienteService avaliacaoSobreClienteService;


    public AvaliacaoSobreClienteController(AvaliacaoSobreClienteService avaliacaoSobreClienteService) {
        this.avaliacaoSobreClienteService = avaliacaoSobreClienteService;
    }


    /*

    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant){
        Optional<Group> group = groupService.findById(participant.getGroup().getId());
        if (group.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<Registration> registration = registrationService.findById(participant.getRegistration().getId());
        if(registration.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Participant newParticipant = new Participant(group.get(), participant.getRegistration(), null);

        Participant savedParticipant = participantService.save(newParticipant);
        return ResponseEntity.ok(savedParticipant);
    }

     */

    @GetMapping('/{clienteId}') // PRECISA AJEITAR O GET MAP ou post map
    public ResponseEntity<List<AvaliacaoSobreCliente>> getAvaliacoesPorCliente(@PathVariable int clienteId){
        List<AvaliacaoSobreCliente> avaliacoes = avaliacaoSobreClienteService.contarAvaliacoesPorCliente(clienteId);
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliacão por cliente encontrada");
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a lista estiver vazia
        }
        return ResponseEntity.ok(avaliacoes);
    }



    @PostMapping
    public ResponseEntity<AvaliacaoSobreCliente> createAvaliacao(@RequestBody AvaliacaoSobreCliente avaliacao) {
        AvaliacaoSobreCliente novaAvaliacao = avaliacaoSobreClienteService.salvarAvaliacao(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao); // Retorna 201 Created
    }


    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoSobreCliente> updateAvaliacao(@PathVariable int id, @RequestBody AvaliacaoSobreCliente avaliacao) {

        Optional<AvaliacaoSobreCliente> avaliacaoExistente = avaliacaoSobreClienteService.buscarPorId(id);

        if (avaliacaoExistente.isPresent()) {

            avaliacao.setId(id);
            AvaliacaoSobreCliente avaliacaoAtualizada = avaliacaoSobreClienteService.salvarAvaliacao(avaliacao);
            return ResponseEntity.ok(avaliacaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 8. Endpoint para deletar uma avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable int id) {
        Optional<AvaliacaoSobreCliente> avaliacaoExistente = avaliacaoSobreClienteService.buscarPorId(id);
        if (avaliacaoExistente.isPresent()) {
            avaliacaoSobreClienteService.deletarAvaliacao(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content para indicar sucesso sem corpo
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se não existir
        }
    }

}
