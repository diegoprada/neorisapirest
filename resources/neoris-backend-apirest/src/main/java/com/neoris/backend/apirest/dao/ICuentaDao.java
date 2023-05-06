package com.neoris.backend.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import com.neoris.backend.apirest.models.entity.Cuenta;

public interface ICuentaDao extends CrudRepository<Cuenta, Long>  {
    Cuenta findByNumeroCuenta(Long cuenta);
}
