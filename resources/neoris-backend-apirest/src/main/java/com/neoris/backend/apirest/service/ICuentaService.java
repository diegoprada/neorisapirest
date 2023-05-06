package com.neoris.backend.apirest.service;

import java.util.List;
import com.neoris.backend.apirest.models.entity.Cuenta;

public interface ICuentaService {
	public List<Cuenta> findAll();
	
	public Cuenta findById(Long id);
	
	public Cuenta save(Cuenta cuenta);
	
	public void delete(Long id);

	public Cuenta findByNumeroCuenta(Long id);
}
