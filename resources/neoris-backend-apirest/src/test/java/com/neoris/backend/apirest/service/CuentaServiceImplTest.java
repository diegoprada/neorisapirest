package com.neoris.backend.apirest.service;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.entity.Cuenta;
import com.neoris.backend.apirest.domain.models.CuentaReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.repository.ClienteRepository;
import com.neoris.backend.apirest.repository.CuentaRepository;

import com.neoris.backend.apirest.service.impl.ClienteServiceImpl;
import com.neoris.backend.apirest.service.impl.CuentaServiceImpl;
import org.junit.Before;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class CuentaServiceImplTest {
    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        //mokeamos los respositories
        cuentaRepository = mock(CuentaRepository.class);

        //inyectamos al servicio
        cuentaService = new CuentaServiceImpl(cuentaRepository);
    }

    @Test
    void operacionEntreCuentas() throws UnknownHostException {
        Cliente cliente1 = new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        Cliente cliente2 = new Cliente( "John Doe2", "Male", 25, "1234567891", "123 Street", "1234567890", "password", "1");

        Cuenta cuenta1 = new Cuenta(1L, 123456789L, "Ahorros", 1000, "1", cliente1);
        Cuenta cuenta2 = new Cuenta(2L, 987654321L, "Corriente", 2000, "1", cliente2);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta1));
        when(cuentaRepository.findById(2L)).thenReturn(Optional.of(cuenta2));

        Double saldoOrigen = cuentaService.revisarSaldo(1L);
        Double saldoDestino = cuentaService.revisarSaldo(2L);

        Assertions.assertEquals("1000.0", saldoOrigen.toString());//convierte a un string
        Assertions.assertEquals("2000.0", saldoDestino.toString());//convierte a un string
        cuentaService.transferir(1L, 2L, 100);

        saldoOrigen = cuentaService.revisarSaldo(1L);
        saldoDestino = cuentaService.revisarSaldo(2L);

        //encuanto quedo el saldo en cada cuenta
        Assertions.assertEquals("900.0", saldoOrigen.toString());
        Assertions.assertEquals("2100.0", saldoDestino.toString());

        //vamos a ver cuantas veces se invoca un repository
        verify(cuentaRepository,times(3)).findById(1L);
        verify(cuentaRepository,times(3)).findById(2L);

    }
    @Test
    public void testFindAll() throws UnknownHostException {

        Cliente cliente1 = new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        Cliente cliente2 = new Cliente( "John Doe2", "Male", 25, "1234567891", "123 Street", "1234567890", "password", "1");

        Cuenta cuenta1 = new Cuenta(1L, 123456789L, "Ahorros", 100.0, "1", cliente1);
        Cuenta cuenta2 = new Cuenta(2L, 987654321L, "Corriente", 500.0, "0", cliente2);
        List<Cuenta> cuentas = Arrays.asList(cuenta1, cuenta2);

        when(cuentaRepository.findAll()).thenReturn(cuentas);

        List<Cuenta> result = cuentaService.findAll();

        assertEquals(cuentas, result);
        verify(cuentaRepository, times(1)).findAll();

    }

    @Test
    public void testFindById() throws UnknownHostException {

        Long id = 1L;
        Cliente cliente1 = new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");

        Cuenta cuenta = new Cuenta(id, 123456789L, "Ahorros", 100.0, "1", cliente1);

        when(cuentaRepository.findById(id)).thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.findById(id);

        assertEquals(cuenta, result);
        verify(cuentaRepository, times(1)).findById(id);


    }

    @Test
    public void testUpdate() throws BadRequestExceptions, UnknownHostException {
        Long id = 1L;

        Cliente cliente1 = new Cliente( "John Doe", "Male", 30, "1234567890", "123 Street", "1234567890", "password", "1");
        Cliente cliente2 = new Cliente( "John Doe2", "Male", 25, "1234567891", "123 Street", "1234567890", "password", "1");


        CuentaReq cuentaReq = new CuentaReq(987654321L, "Corriente", 500.0, "0", cliente2);
        Cuenta cuentaActual = new Cuenta(id, 123456789L, "Ahorros", 100.0, "1", cliente1);

        when(cuentaRepository.findById(id)).thenReturn(Optional.of(cuentaActual));
        when(cuentaRepository.save(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cuenta result = cuentaService.update(cuentaReq, id);

        assertEquals(cuentaReq.getTipoCuenta(), result.getTipoCuenta());
        assertEquals(cuentaReq.getSaldoInicial(), result.getSaldoInicial(), 0.01);
        assertEquals(cuentaReq.getEstado(), result.getEstado());
        assertEquals(cuentaReq.getIdCliente(), result.getIdCliente());

        verify(cuentaRepository, times(1)).findById(id);
        verify(cuentaRepository, times(1)).save(cuentaActual);
    }

    @Test
    public void testUpdate_NonexistentCuenta() throws BadRequestExceptions, UnknownHostException {
        Long id = 1L;
        Cliente cliente2 = new Cliente( "John Doe", "Male", 30, "1234567891", "123 Street", "1234567890", "password", "1");

        CuentaReq cuentaReq = new CuentaReq(987654321L, "Corriente", 500.0, "0", cliente2);

        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BadRequestExceptions.class, () -> cuentaService.update(cuentaReq, id));

        verify(cuentaRepository, times(1)).findById(id);
        verify(cuentaRepository, never()).save(any(Cuenta.class));
    }


}
