package com.neoris.backend.apirest.repository;

import org.springframework.data.repository.CrudRepository;
import com.neoris.backend.apirest.domain.entity.Cuenta;

public interface CuentaRepository extends CrudRepository<Cuenta, Long>  {
    Cuenta findByNumeroCuenta(Long cuenta);
}
