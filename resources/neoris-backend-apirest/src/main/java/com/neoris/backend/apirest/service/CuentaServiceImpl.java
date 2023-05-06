package com.neoris.backend.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.backend.apirest.dao.ICuentaDao;
import com.neoris.backend.apirest.models.entity.Cliente;
import com.neoris.backend.apirest.models.entity.Cuenta;

@Service
public class CuentaServiceImpl implements ICuentaService {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findAll() {
		return (List<Cuenta>) cuentaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id) {
		return cuentaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return cuentaDao.save(cuenta);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cuentaDao.deleteById(id);
	}

	@Override
	public Cuenta findByNumeroCuenta(Long id) {
		return cuentaDao.findByNumeroCuenta(id);
	}

}
