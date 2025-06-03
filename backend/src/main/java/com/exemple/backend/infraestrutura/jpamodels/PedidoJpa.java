package com.exemple.backend.infraestrutura.jpamodels;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class PedidoJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "fk_servicos_nome", referencedColumnName = "nome")
    private ServicoJpa servico;

    @ManyToOne
    @JoinColumn(name = "fk_prestador_id", referencedColumnName = "id")
    private PrestadorJpa prestador;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "id")
    private ClienteJpa cliente;

    @Column(nullable = false)
    private String status;

    public PedidoJpa(){

    }

    public PedidoJpa(Integer id, LocalDate data, ServicoJpa servico, PrestadorJpa prestador, ClienteJpa cliente, String status) {
        this.id = id;
        this.data = data;
        this.servico = servico;
        this.prestador = prestador;
        this.cliente = cliente;
        this.status = status;
    }

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

    public ServicoJpa getServico() {
        return servico;
    }

    public void setServico(ServicoJpa servico) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "PedidoJpa{id=%d, data=%s, status='%s', servico='%s', prestadorId=%s, clienteId=%s}",
                id,
                data,
                status,
                servico != null ? servico.getNome() : null,
                prestador != null ? prestador.getId() : null,
                cliente != null ? cliente.getId() : null
        );
    }

}
