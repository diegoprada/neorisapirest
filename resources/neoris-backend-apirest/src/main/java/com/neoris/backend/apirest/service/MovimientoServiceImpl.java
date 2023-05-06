package com.neoris.backend.apirest.service;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.backend.apirest.dao.IMovimientoDao;
import com.neoris.backend.apirest.models.entity.Movimiento;

@Service 
public class MovimientoServiceImpl implements IMovimientoService {

	@Autowired
	private IMovimientoDao movimientoDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Movimiento> findAll() {
		return (List<Movimiento>) movimientoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Movimiento findById(Long id) {
		return movimientoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Movimiento save(Movimiento movimiento) {
		return movimientoDao.save(movimiento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		movimientoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Movimiento> findByIdfstartFend(Long id, Date fstart, Date fend) {
		return movimientoDao.findByIdAndfindAllByFechaBetween(id, fstart, fend);
	}

}
