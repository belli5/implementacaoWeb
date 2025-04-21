package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.prestacaoServico.Servico;
import com.exemple.implementacaoweb2.prestacaoServico.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ServicoServiceTest {

    private ServicoService servicoService;

    @BeforeEach
    public void setUp() {
        servicoService = new ServicoService();

        servicoService.adicionarServico(new Servico(1, "Instalação de tomadas", "Eletricista"));
        servicoService.adicionarServico(new Servico(2, "Reparo em encanamento", "Encanador"));
        servicoService.adicionarServico(new Servico(3, "Troca de disjuntor", "Eletricista"));
    }

    @Test
    public void testFiltrarServicosPorCategoria() {

        List<Servico> filtrados = servicoService.filtrarPorCategoria("Eletricista");

        assertEquals(2, filtrados.size());
        for (Servico servico : filtrados) {
            assertEquals("Eletricista", servico.getCategoria());
        }
    }
}