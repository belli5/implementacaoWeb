package com.exemple.backend.dominio.models;

import java.io.Serializable;
import java.time.LocalDate;

import static org.springframework.util.Assert.notNull;

public class Pedido implements Serializable {

    private Integer id;
    private LocalDate data;
    private Servico servico;
    private Prestador prestador;
    private Cliente cliente;
    private String status;



    public Pedido() {
    }

    public Pedido(Integer id, LocalDate data, Servico servico, Prestador prestador, Cliente cliente, String status) {
        notNull(id, "Id do pedido não pode ser nulo");
        notNull(data, "Data do pedido não pode ser nula");
        notNull(servico, "Serviço não pode ser nulo");
        notNull(prestador, "Prestador não pode ser nulo");
        notNull(cliente, "Cliente não pode ser nulo");
        notNull(status, "Status do pedido não pode ser nulo");

        this.id = id;
        this.data = data;
        this.servico = servico;
        this.prestador = prestador;
        this.cliente = cliente;
        this.status = status;
    }

    // Getters e setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format(
                "Pedido{id=%d, data=%s, status='%s', servico=%s, prestador=%s, cliente=%s}",
                id, data, status, servico, prestador, cliente
        );
    }
}
