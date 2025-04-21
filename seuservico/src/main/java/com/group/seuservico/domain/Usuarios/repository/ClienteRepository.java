package com.group.seuservico.domain.Usuarios.repository;

import com.group.seuservico.domain.Usuarios.model.Cliente;

public interface ClienteRepository {

     Cliente save(Cliente cliente);
     void delete(int id);
     void update(int id);
    
}
