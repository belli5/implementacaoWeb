package com.seuservico.infraestrutura.persistencia.jpa.pedidojpa;

import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import com.seuservico.infraestrutura.persistencia.jpa.clientejpa.ClienteJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa.PrestacaoServicoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
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

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
