package com.neoris.backend.apirest.service;


import java.sql.Date;
import java.util.List;
import com.neoris.backend.apirest.models.entity.Movimiento;

public interface IMovimientoService {
	public List<Movimiento> findAll();

	public Movimiento findById(Long id);

	public Movimiento save(Movimiento cuenta);

	public void delete(Long id);
	
	public List<Movimiento> findByIdfstartFend(Long id, Date fstart, Date fend);
}
