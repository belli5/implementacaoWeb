package com.exemple.backend.dominio.repositorys;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoSobreClienteRepository {
    //Precisa??????????
    public Optional<AvaliacaoSobreCliente> findById(int id);
    public List<AvaliacaoSobreCliente> findAll();
    //-----------------
    public List<AvaliacaoSobreCliente> findByClienteId(int cliente_Id);
    public AvaliacaoSobreCliente save(AvaliacaoSobreCliente avaliacaoSobreCliente);
    public void delete(int id);

}
