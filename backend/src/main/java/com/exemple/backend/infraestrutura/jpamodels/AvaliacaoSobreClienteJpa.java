package com.exemple.backend.infraestrutura.jpamodels;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table (name = "avaliacao_sobre_cliente")
public class AvaliacaoSobreClienteJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Id_Prestador", referencedColumnName = "id")
    private PrestadorJpa prestador;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private int nota;

    @ManyToOne
    @JoinColumn(name = "Id_Cliente", referencedColumnName = "id")
    private ClienteJpa cliente;

    public AvaliacaoSobreClienteJpa(){

    }

    public AvaliacaoSobreClienteJpa(int id, PrestadorJpa prestador, String comentario, int nota, ClienteJpa cliente) {
        this.id = id;
        this.prestador = prestador;
        this.comentario = comentario;
        this.nota = nota;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PrestadorJpa getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorJpa prestador) {
        this.prestador = prestador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public ClienteJpa getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJpa cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format(
                "AvaliacaoSobreClienteJpa{id=%d, nota=%d, comentario='%s', clienteId=%d, prestadorId=%d}",
                id,
                nota,
                comentario,
                cliente != null ? cliente.getId() : null,
                prestador != null ? prestador.getId() : null
        );
    }

}
