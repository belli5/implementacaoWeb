package com.exemple.implementacaoweb2.pedidos;
import java.time.LocalDate;

public class Pedido {

    private int id;
    private int prestadorId;
    private int clienteId;
    private LocalDate data;
    private StatusPedido status;

    public Pedido(int id, int prestadorId, int clienteId, LocalDate data, StatusPedido status) {
        this.id = id;
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

    public LocalDate getData() {
        return data;
    }

    public StatusPedido getStatus() {
        return status;
    }

}
