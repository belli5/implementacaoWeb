package com.exemple.implementacaoweb2.pedidos;
import java.time.LocalDate;

public class Pedido {

    private final int id;
    private final int prestadorId;
    private final int clienteId;
    private final LocalDate data;
    private final String status;

    public Pedido(int id, int prestadorId, int clienteId, LocalDate data, String status) {
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

    public String getStatus() {
        return status;
    }

}
