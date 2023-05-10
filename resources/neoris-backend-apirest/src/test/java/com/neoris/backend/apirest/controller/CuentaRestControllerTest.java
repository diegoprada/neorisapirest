package com.neoris.backend.apirest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.backend.apirest.controllers.CuentaRestController;
import com.neoris.backend.apirest.domain.entity.Cuenta;
import com.neoris.backend.apirest.service.ICuentaService;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CuentaRestController.class)
public class CuentaRestControllerTest {

	@MockBean
	private ICuentaService cuentaService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private List<Cuenta> cuentas;
	

	@Test
	public void show_debeRetornarNotFoundCuandoNoExisteId() throws Exception {
		given(cuentaService.findById(anyLong())).willReturn(null);

		mockMvc.perform(get("/api/cuentas/999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("El Cuenta ID: no existe en la base de datos")));


	}
	
	/*
	 @Test
	    public void show_debeRetornarCuentaCuandoExisteId() throws Exception {
	        given(cuentaService.findById(anyLong())).willReturn(cuentas.get(0));

	        mockMvc.perform(get("/api/cuentas/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.tipoCuenta", is("ahorro")))
	                .andExpect(jsonPath("$.saldoInicial", is(1000.0)))
	                .andExpect(jsonPath("$.estado", is("1")))
	                .andExpect(jsonPath("$.idCliente", is(1)));
	    }
	    */

}
