package com.neoris.backend.apirest.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.backend.apirest.exceptions.MovimentosError;
import com.neoris.backend.apirest.models.entity.Cuenta;
import com.neoris.backend.apirest.models.entity.Movimiento;
import com.neoris.backend.apirest.service.ICuentaService;
import com.neoris.backend.apirest.service.IMovimientoService;

@RestController
@RequestMapping("/api")
public class MovimientosRestController {

    @Autowired
    private IMovimientoService movimientoService;

    @Autowired
    private ICuentaService cuentaService;

    @GetMapping("/movimientos")
    public List<Movimiento> index() {
        return movimientoService.findAll();
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Movimiento movimiento = null;
        Map<String, Object> response = new HashMap<>();
        try {
            movimiento = movimientoService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (movimiento == null) {
            response.put("mensaje", "El Movimiento ID:".concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Movimiento>(movimiento, HttpStatus.OK);
    }

    @PostMapping("/movimientos")
    public ResponseEntity<?> create(@RequestBody Movimiento movimiento) {
        Movimiento movimientoNew = null;
        Cuenta cuenta = null;
        Map<String, Object> response = new HashMap<>();

        try {

            cuenta = cuentaService.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());

            if (cuenta.getSaldoInicial() == 0 && movimiento.getValor() < 0) {
                response.put("mensaje", "Saldo no disponible !");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            if (movimiento.getValor() < 0) {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() - (movimiento.getValor() * -1));
            } else {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());

            }
            movimiento.setCuenta(cuenta);
            movimiento.setSaldo(cuenta.getSaldoInicial());
            movimientoNew = movimientoService.save(movimiento);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "Movimiento ha sido creado con exíto !");
        response.put("Movimiento", movimientoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/movimientos/{id}")
    public ResponseEntity<?> update(@RequestBody Movimiento movimiento, @PathVariable Long id) {
        Movimiento movimientoActual = movimientoService.findById(id);
        Movimiento movimientoUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (movimientoActual == null) {
            response.put("mensaje",
                    "Error: no se puede editar el Movimiento ID:".concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            movimientoActual.setFecha(movimiento.getFecha());
            movimientoActual.setTipoMovimiento(movimiento.getTipoMovimiento());
            movimientoActual.setValor(movimiento.getValor());
            movimientoActual.setSaldo(movimiento.getSaldo());
            movimientoActual.setCuenta(movimiento.getCuenta());
            movimientoUpdated = movimientoService.save(movimientoActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el Movimiento en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Movimiento ha sido actualizado con exíto !");
        response.put("Movimiento", movimientoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/movimientos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            movimientoService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el Movimiento en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);// 404

        }
        response.put("mensaje", "El Movimiento eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
