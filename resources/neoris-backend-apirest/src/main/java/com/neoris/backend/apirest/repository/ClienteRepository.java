package com.neoris.backend.apirest.repository;

import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.models.ClienteReq;
import org.springframework.data.repository.CrudRepository;


public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
