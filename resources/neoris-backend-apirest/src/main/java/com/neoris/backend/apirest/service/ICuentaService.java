package com.neoris.backend.apirest.service;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.List;
import com.neoris.backend.apirest.domain.entity.Cuenta;
import com.neoris.backend.apirest.domain.models.CuentaReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;

public interface ICuentaService {
	public List<Cuenta> findAll() throws UnknownHostException;
	
	public Cuenta findById(Long id) throws UnknownHostException;
	
	public Cuenta save(CuentaReq cuenta) throws UnknownHostException;
	
	public void delete(Long id) throws UnknownHostException;

	public Cuenta findByNumeroCuenta(Long id) throws UnknownHostException;

	public Cuenta update(CuentaReq cuenta, Long id) throws UnknownHostException, BadRequestExceptions;

	double revisarSaldo(long idCuenta) throws UnknownHostException;

	void transferir(Long numCuentaOrigen, Long numCuentaDestino, Integer monto) throws UnknownHostException;
}
