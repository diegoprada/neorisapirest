package com.neoris.backend.apirest.dao;

import org.springframework.data.repository.CrudRepository;

import com.neoris.backend.apirest.models.entity.Cliente;



public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
