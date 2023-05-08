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

/**
 * Controlador REST para generar reportes de movimientos.
 * Este controlador utiliza el servicio IMovimientoService para realizar consultas en la base de datos.
 */
@RestController
@RequestMapping("/api")
public class ReporteRestController {

    @Autowired
    private IMovimientoService movimientoService;

    /**
     * Genera un reporte de movimientos para una cuenta específica en un rango de fechas.
     * Si la cuenta no existe o no tiene movimientos en el rango de fechas especificado,
     * retorna una respuesta con estado HTTP 404 (NOT_FOUND).
     *
     * @param id     identificador de la cuenta.
     * @param fstart fecha de inicio del rango.
     * @param fend   fecha de fin del rango.
     * @return lista de movimientos correspondientes al reporte.
     */
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
