package com.neoris.backend.apirest.service;


import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;
import com.neoris.backend.apirest.domain.entity.Movimiento;
import com.neoris.backend.apirest.domain.models.MovimientoReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;

public interface IMovimientoService {
	public List<Movimiento> findAll() throws UnknownHostException;

	public Movimiento findById(Long id) throws UnknownHostException;

	public Movimiento save(MovimientoReq movimiento) throws UnknownHostException, BadRequestExceptions;

	public void delete(Long id) throws UnknownHostException;
	
	public List<Movimiento> findByIdfstartFend(Long id, Date fstart, Date fend) throws UnknownHostException, BadRequestExceptions;

	public Movimiento update(Movimiento movimiento, Long id) throws BadRequestExceptions, UnknownHostException;
}
