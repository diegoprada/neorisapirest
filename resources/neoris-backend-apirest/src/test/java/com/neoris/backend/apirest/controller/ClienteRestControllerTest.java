package com.neoris.backend.apirest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.neoris.backend.apirest.domain.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.backend.apirest.controllers.ClienteRestController;
import com.neoris.backend.apirest.service.IClienteService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteRestController.class)
public class ClienteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IClienteService clienteService;

    @Test
    public void testIndex() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Juan", "M", 30, "1234567", "cr24", "55555", "1234", "1"));
		clientes.add(new Cliente("Paola", "F", 30, "1234568", "cr24", "444", "1234", "1"));
        
		Mockito.when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clientes)));
    }

    @Test
    public void testShow() throws Exception {
        Long id = 1L;
        Cliente cliente = new Cliente("Juan", "M", 30, "1234567", "cr24", "55555", "1234", "1");

        Mockito.when(clienteService.findById(id)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cliente)));
    }

    @Test
    public void testCreate() throws Exception {
        Cliente cliente = new Cliente("Juan", "M", 30, "1234567", "cr24", "55555", "1234", "1");
        mockMvc.perform(post("/api/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cliente)))
        .andExpect(status().isCreated())
        .andDo(print());
        
    }
	
}

