package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.repositorys.OfereceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfereceService {

    private final OfereceRepository ofereceRepository;

    public OfereceService(OfereceRepository ofereceRepository) {
        this.ofereceRepository = ofereceRepository;
    }

    public List<Oferece> listarTodos() {
        return ofereceRepository.findAll();
    }

    public Optional<Oferece> buscarPorId(int id) {
        return ofereceRepository.findById(id);
    }

    public List<Oferece> buscarPorPrestadorId(int prestadorId) {
        return ofereceRepository.findByPrestadorId(prestadorId);
    }

    public List<Oferece> buscarPorServicoNome(String servicoNome) {
        return ofereceRepository.findByServicoNome(servicoNome);
    }

    public Oferece salvar(Oferece oferece) {
        return ofereceRepository.save(oferece);
    }

    public void deletar(int id) {
        ofereceRepository.delete(id);
    }
}
