package com.neoris.backend.apirest.service;

import com.neoris.backend.apirest.domain.entity.Cliente;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.models.ClienteReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.repository.ClienteRepository;


import com.neoris.backend.apirest.service.impl.ClienteServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        //mokeamos los respositories
        clienteRepository = mock(ClienteRepository.class);

        //inyectamos al servicio
        clienteService = new ClienteServiceImpl(clienteRepository);
    }
    @Test
    public void testFindAll() throws UnknownHostException {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1"));

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertEquals(clientes, result);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() throws UnknownHostException {
        Long id = 1L;
        Cliente cliente = new Cliente("John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(id);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() throws UnknownHostException {
        ClienteReq clienteReq = new ClienteReq("John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        Cliente cliente = new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.save(clienteReq);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testDelete() throws UnknownHostException {
        Long id = 1L;

        clienteService.delete(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }


    @Test
    public void testUpdate_NonexistentCliente() throws BadRequestExceptions, UnknownHostException {
        Long id = 1L;
        ClienteReq clienteReq = new ClienteReq("John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        //BadRequestExceptions exception =assertThrows (BadRequestExceptions.class, () ->  clienteService.update(clienteReq, id));
        //assertEquals("EL CIENTE NO EXISTE", exception.getMessage());
        assertThrows(BadRequestExceptions.class, () -> clienteService.update(clienteReq, id));

    }

    @Test
    public void testUpdate_ExistingCliente() throws BadRequestExceptions, UnknownHostException {
        Long id = 1L;
        ClienteReq clienteReq = new ClienteReq("John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        Cliente clienteActual =new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteActual));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente result = clienteService.update(clienteReq, id);

        assertEquals(clienteReq.getNombre(), result.getNombre());
        assertEquals(clienteReq.getGenero(), result.getGenero());
        assertEquals(clienteReq.getEdad(), result.getEdad());
        assertEquals(clienteReq.getDireccion(), result.getDireccion());
        assertEquals(clienteReq.getTelefono(), result.getTelefono());
        assertEquals(clienteActual.getEstado(), result.getEstado());

        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

}
