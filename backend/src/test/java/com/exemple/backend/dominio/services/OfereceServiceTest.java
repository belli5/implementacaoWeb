package com.exemple.backend.dominio.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import com.exemple.backend.dominio.repositorys.OfereceRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OfereceServiceTest {

    // @Mock
    // private OfereceRepository ofereceRepositoryMock;

    @InjectMocks
    private OfereceService ofereceService;

    @Test
    void exemploTesteOfereceService() {
        // Teste para quando a lógica for implementada
        // assertTrue(true);
        assertNotNull(ofereceService, "OfereceService não deveria ser nulo, mesmo que vazio.");
    }
}