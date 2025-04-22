package com.exemple.implementacaoweb2.pedidos;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pedido {

    private int id;
    private PrestacaoServico servico;
    private int prestadorId;
    private int clienteId;
    private LocalDateTime data;
    private StatusPedido status;


    public Pedido(int id, PrestacaoServico servico, int prestadorId, int clienteId, LocalDateTime data, StatusPedido status) {
        this.id = id;
        this.servico = servico;
        this.prestadorId = prestadorId;
        this.clienteId = clienteId;
        this.data = data;
        this.status = status;

    }

    public int getId() {
        return id;
    }

    public int getPrestadorId() {
        return prestadorId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setData(LocalDate data) {
        this.data = data.atStartOfDay();
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public PrestacaoServico getServico() {
        return servico;
    }

    public void setId(long l) {
    }

    public void setServico(PrestacaoServico servico) {
    }

    public void setDataHora(LocalDateTime localDateTime) {
    }
}
