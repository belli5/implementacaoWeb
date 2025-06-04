package com.exemple.backend.dominio.services.template;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.repositorys.ClienteRepository;
import com.exemple.backend.dominio.strategies.ClienteValidadorStrategy;
import org.springframework.stereotype.Component;

@Component
public class CadastroCliente extends CadastroTemplate<Cliente> {

    private final ClienteRepository clienteRepository;
    private final ClienteValidadorStrategy validador;

    public CadastroCliente(ClienteRepository clienteRepository, ClienteValidadorStrategy validador) {
        this.clienteRepository = clienteRepository;
        this.validador = validador;
    }

    @Override
    protected void validar(Cliente cliente) {
        validador.validar(cliente);
    }

    @Override
    protected Cliente preparar(Cliente cliente) {
        // Aqui você pode formatar dados, limpar espaços etc.
        cliente.setNome(cliente.getNome().trim());
        return cliente;
    }

    @Override
    protected void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    protected void posCadastro(Cliente cliente) {
        System.out.println("Cliente criado: " + cliente.getNome());
    }
}