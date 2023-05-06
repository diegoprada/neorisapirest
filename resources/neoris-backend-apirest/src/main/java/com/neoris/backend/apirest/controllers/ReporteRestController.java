package com.neoris.backend.apirest.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.backend.apirest.models.entity.Movimiento;
import com.neoris.backend.apirest.service.IMovimientoService;

@RestController
@RequestMapping("/api")
public class ReporteRestController {

	@Autowired
	private IMovimientoService movimientoService;

	@GetMapping("/reportes/{id}/{fstart}/{fend}")
	public ResponseEntity<?> index(@PathVariable Long id, @PathVariable Date fstart, @PathVariable Date fend) {
		Map<String, Object> response = new HashMap<>();
		List<Movimiento> reporte = movimientoService.findByIdfstartFend(id, fstart, fend);

		if (reporte.isEmpty()) {

			response.put("mensaje", "La Cuenta # ".concat("" + id).concat(" no existe registros en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List>(reporte, HttpStatus.OK);
	}

}
