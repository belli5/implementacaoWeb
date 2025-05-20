package com.seuservico.infraestrutura.persistencia.jpa;

import com.exemple.implementacaoweb2.cliente.Cliente;
import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.Prestador;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class PedidoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private PrestacaoServicoJpa servico;

    @ManyToOne
    private PrestadorJpa prestador;

    @ManyToOne
    private ClienteJpa cliente;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public PedidoJpa() {
    }

    public PedidoJpa(int id, PrestacaoServicoJpa servico, PrestadorJpa prestador, ClienteJpa cliente, LocalDateTime data, StatusPedido status) {
        this.id = id;
        this.servico = servico;
        this.prestador = prestador;
        this.cliente = cliente;
        this.data = data;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public PrestacaoServicoJpa getServico() {
        return servico;
    }

    public void setServico(PrestacaoServicoJpa servico) {
        this.servico = servico;
    }

    public PrestadorJpa getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorJpa prestador) {
        this.prestador = prestador;
    }

    public ClienteJpa getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJpa cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}
