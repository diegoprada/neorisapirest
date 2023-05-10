package com.neoris.backend.apirest.service;

import java.net.UnknownHostException;
import java.util.List;

import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.models.ClienteReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;



public interface IClienteService {
	public List<Cliente> findAll() throws UnknownHostException;
	
	public Cliente findById(Long id) throws UnknownHostException;
	
	public Cliente save(ClienteReq cliente) throws UnknownHostException;
	
	public void delete(Long id) throws UnknownHostException;

	public Cliente update(ClienteReq cliente, Long id) throws BadRequestExceptions, UnknownHostException;
	
}
