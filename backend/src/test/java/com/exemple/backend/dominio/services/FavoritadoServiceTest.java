package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritadoServiceTest {

    @Mock
    private FavoritadoRepository repositoryMock;

    @InjectMocks
    private FavoritadoService service;

    private Favoritado favoritado1;
    private Cliente cliente;
    private Prestador prestador;
    private Endereco endereco;


    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Fav", "Bairro Fav", "Cidade Fav", "FV");
        cliente = new Cliente(1, "Cliente Fav", "s", "c@fav.com", "t", endereco);
        prestador = new Prestador(1, "Prestador Fav", "s", "p@fav.com", "t", endereco);
        favoritado1 = new Favoritado(1, cliente, prestador);
    }

    @Test
    void deveBuscarFavoritadoPorIdExistente() {
        when(repositoryMock.findById(1)).thenReturn(Optional.of(favoritado1));
        Optional<Favoritado> resultado = service.buscarPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(favoritado1.getCliente().getNome(), resultado.get().getCliente().getNome());
        verify(repositoryMock, times(1)).findById(1);
    }

    @Test
    void deveListarTodosOsFavoritados() {
        Favoritado favoritado2 = new Favoritado(2, cliente, new Prestador(2, "Outro P", "s", "p2@fav.com", "t2", endereco));
        List<Favoritado> lista = Arrays.asList(favoritado1, favoritado2);
        when(repositoryMock.findAll()).thenReturn(lista);

        List<Favoritado> resultado = service.listarTodos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    void deveListarFavoritosDoCliente() {
        List<Prestador> listaPrestadores = Arrays.asList(prestador);
        when(repositoryMock.findPrestadoresFavoritadosByClienteId(1)).thenReturn(listaPrestadores);

        List<Prestador> resultado = service.listarFavoritosDoCliente(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(prestador.getNome(), resultado.get(0).getNome());
        verify(repositoryMock, times(1)).findPrestadoresFavoritadosByClienteId(1);
    }

    @Test
    void deveListarClientesQueFavoritaramPrestador() {
        List<Cliente> listaClientes = Arrays.asList(cliente);
        when(repositoryMock.findClientesQueFavoritaramPrestadorByPrestadorId(1)).thenReturn(listaClientes);

        List<Cliente> resultado = service.listarClientesQueFavoritaramPrestador(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(cliente.getNome(), resultado.get(0).getNome());
        verify(repositoryMock, times(1)).findClientesQueFavoritaramPrestadorByPrestadorId(1);
    }

    @Test
    void deveFavoritarComSucesso() {
        when(repositoryMock.save(any(Favoritado.class))).thenReturn(favoritado1);
        Favoritado salvo = service.favoritar(favoritado1);
        assertNotNull(salvo);
        assertEquals(favoritado1.getId(), salvo.getId());
        verify(repositoryMock, times(1)).save(favoritado1);
    }

    @Test
    void deveDesfavoritarComSucesso() {
        doNothing().when(repositoryMock).delete(1);
        service.desfavoritar(1);
        verify(repositoryMock, times(1)).delete(1);
    }
}