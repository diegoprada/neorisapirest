package com.neoris.backend.apirest.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neoris.backend.apirest.domain.entity.Movimiento;

public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {
	@Query("SELECT t FROM  Movimiento t , Cuenta c WHERE  t.cuenta.id=c.id AND c.idCliente.id=:id AND t.fecha BETWEEN :start AND :end")
	List<Movimiento> findByIdAndfindAllByFechaBetween(@Param("id")Long id,  @Param("start") Date start,@Param("end") Date end);
}
