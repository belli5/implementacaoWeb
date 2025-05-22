package com.seuservico.infraestrutura.persistencia.jpa;

import com.exemple.implementacaoweb2.avaliacao.Avaliacao;
import com.exemple.implementacaoweb2.cliente.Cliente;
import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.pedidos.Pedido;
import com.exemple.implementacaoweb2.prestador.Prestador;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.seuservico.infraestrutura.persistencia.jpa.avaliacaojpa.AvaliacaoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.clientejpa.ClienteJpa;
import com.seuservico.infraestrutura.persistencia.jpa.enderecojpa.EnderecoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.pedidojpa.PedidoJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestadorjpa.PrestadorJpa;
import com.seuservico.infraestrutura.persistencia.jpa.prestacaoservicojpa.PrestacaoServicoJpa;
import java.util.Map;


import java.util.List;
import java.util.stream.Collectors;


public class MapperGeral {

    // ===========================
    // 🔹 Avaliacao ↔️ AvaliacaoJpa
    // ===========================
    public static AvaliacaoJpa toAvaliacaoJpa(Avaliacao avaliacao) {
        AvaliacaoJpa avaliacaoJpa = new AvaliacaoJpa();
        avaliacaoJpa.setId(avaliacao.getId());
        avaliacaoJpa.setPrestadorId(avaliacao.getPrestadorId());
        avaliacaoJpa.setClienteId(avaliacao.getClienteId());
        avaliacaoJpa.setNota(avaliacao.getNota());
        avaliacaoJpa.setComentario(avaliacao.getComentario());
        avaliacaoJpa.setTipoAvaliacao(avaliacao.getTipoAvaliacao());
        return avaliacaoJpa;
    }

    public static Avaliacao toAvaliacao(AvaliacaoJpa avaliacaoJpa) {
        return new Avaliacao(
                avaliacaoJpa.getId(),
                avaliacaoJpa.getPrestadorId(),
                avaliacaoJpa.getClienteId(),
                avaliacaoJpa.getNota(),
                avaliacaoJpa.getComentario(),
                avaliacaoJpa.getTipoAvaliacao()
        );
    }

    // ===========================
    // 🔸 Cliente ↔️ ClienteJpa
    // ===========================
    public static ClienteJpa toClienteJpa(Cliente cliente) {
        ClienteJpa clienteJpa = new ClienteJpa();
        clienteJpa.setId(cliente.getId());
        clienteJpa.setNome(cliente.getNome());
        clienteJpa.setEmail(cliente.getEmail());
        clienteJpa.setTelefone(cliente.getTelefone());

        clienteJpa.setEndereco(toEnderecoJpa(cliente.getEndereco()));

        // Mapeia os prestadores favoritos
        List<PrestadorJpa> prestadoresFavoritosJpa = cliente.getPrestadoresFavoritos().stream()
                .map(MapperGeral::toPrestadorJpa)
                .collect(Collectors.toList());
        clienteJpa.setPrestadoresFavoritos(prestadoresFavoritosJpa);

        // Cria um mapa de id → PrestadorJpa para facilitar na busca
        Map<Integer, PrestadorJpa> mapaPrestadores = prestadoresFavoritosJpa.stream()
                .collect(Collectors.toMap(PrestadorJpa::getId, p -> p));

        // Mapeia o histórico de serviços
        clienteJpa.setHistoricoDeServicos(
                cliente.getHistoricoDeServicos().stream()
                        .map(servico -> {
                            PrestadorJpa prestadorJpa = mapaPrestadores.get(servico.getPrestadorId());
                            if (prestadorJpa == null) {
                                throw new RuntimeException("Prestador não encontrado para o serviço de ID: " + servico.getId());
                            }
                            return toPrestacaoServicoJpa(servico, prestadorJpa);
                        })
                        .collect(Collectors.toList())
        );

        return clienteJpa;
    }



    public static Cliente toCliente(ClienteJpa clienteJpa) {
        return new Cliente(
                clienteJpa.getId(),
                clienteJpa.getNome(),
                clienteJpa.getEmail(),
                clienteJpa.getTelefone(),
                toEndereco(clienteJpa.getEndereco()),
                clienteJpa.getPrestadoresFavoritos().stream()
                        .map(MapperGeral::toPrestador)
                        .collect(Collectors.toList()),
                clienteJpa.getHistoricoDeServicos().stream()
                        .map(MapperGeral::toPrestacaoServico)
                        .collect(Collectors.toList())
        );
    }

    // ===========================
    // 🔸 Endereco ↔️ EnderecoJpa
    // ===========================
    public static EnderecoJpa toEnderecoJpa(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoJpa(
                endereco.getRua(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado()
        );
    }

    public static Endereco toEndereco(EnderecoJpa enderecoJpa) {
        if (enderecoJpa == null) return null;
        return new Endereco(
                enderecoJpa.getRua(),
                enderecoJpa.getBairro(),
                enderecoJpa.getCidade(),
                enderecoJpa.getEstado()
        );
    }

    // ===========================
    // 🔸 Pedido ↔️ PedidoJpa
    // ===========================
    public static PedidoJpa toPedidoJpa(Pedido pedido, ClienteJpa clienteJpa, PrestadorJpa prestadorJpa, PrestacaoServicoJpa servicoJpa) {
        PedidoJpa pedidoJpa = new PedidoJpa();
        pedidoJpa.setId(pedido.getId());
        pedidoJpa.setCliente(clienteJpa);
        pedidoJpa.setPrestador(prestadorJpa);
        pedidoJpa.setServico(servicoJpa);
        pedidoJpa.setData(pedido.getData());
        pedidoJpa.setStatus(pedido.getStatus());
        return pedidoJpa;
    }

    public static Pedido toPedido(PedidoJpa pedidoJpa) {
        return new Pedido(
                pedidoJpa.getId(),
                toPrestacaoServico(pedidoJpa.getServico()),
                pedidoJpa.getPrestador().getId(),
                pedidoJpa.getCliente().getId(),
                pedidoJpa.getData(),
                pedidoJpa.getStatus()
        );
    }

    // ===========================
    // 🔸 PrestacaoServico ↔️ PrestacaoServicoJpa
    // ===========================
    public static PrestacaoServicoJpa toPrestacaoServicoJpa(PrestacaoServico servico, PrestadorJpa prestadorJpa) {
        PrestacaoServicoJpa servicoJpa = new PrestacaoServicoJpa();
        servicoJpa.setId(servico.getId());
        servicoJpa.setDescricao(servico.getDescricao());
        servicoJpa.setValor(servico.getValor());
        servicoJpa.setBairro(servico.getBairro());
        servicoJpa.setCategoria(servico.getCategoria());
        servicoJpa.setPrestador(prestadorJpa);
        return servicoJpa;
    }


    public static PrestacaoServico toPrestacaoServico(PrestacaoServicoJpa servicoJpa) {
        return new PrestacaoServico(
                servicoJpa.getId(),
                servicoJpa.getDescricao(),
                servicoJpa.getValor(),
                servicoJpa.getBairro(),
                servicoJpa.getCategoria(),
                servicoJpa.getPrestador().getId()
        );
    }


    // ===========================
    // 🔸 Prestador ↔️ PrestadorJpa
    // ===========================
    public static PrestadorJpa toPrestadorJpa(Prestador prestador) {
        PrestadorJpa prestadorJpa = new PrestadorJpa();
        prestadorJpa.setId(prestador.getId());
        prestadorJpa.setNome(prestador.getNome());
        prestadorJpa.setEmail(prestador.getEmail());
        prestadorJpa.setTelefone(prestador.getTelefone());
        prestadorJpa.setEndereco(toEnderecoJpa(prestador.getEndereco()));

        prestadorJpa.setServicos(
                prestador.getServicos().stream()
                        .map(s -> toPrestacaoServicoJpa(s, prestadorJpa)) // ⚠️ Importante: passa o prestadorJpa aqui
                        .collect(Collectors.toList())
        );

        return prestadorJpa;
    }


    public static Prestador toPrestador(PrestadorJpa prestadorJpa) {
        return new Prestador(
                prestadorJpa.getId(),
                prestadorJpa.getNome(),
                prestadorJpa.getServicos().stream()
                        .map(MapperGeral::toPrestacaoServico)
                        .collect(Collectors.toList()),
                prestadorJpa.getEmail(),
                prestadorJpa.getTelefone(),
                toEndereco(prestadorJpa.getEndereco())
        );
    }


}
