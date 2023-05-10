package com.neoris.backend.apirest.controllers;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neoris.backend.apirest.domain.models.MovimientoReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.service.impl.ClienteServiceImpl;
import com.neoris.backend.apirest.util.LoggerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.neoris.backend.apirest.domain.entity.Cuenta;
import com.neoris.backend.apirest.domain.entity.Movimiento;
import com.neoris.backend.apirest.service.ICuentaService;
import com.neoris.backend.apirest.service.IMovimientoService;

/**
 * Controlador REST para manejar las operaciones CRUD sobre la entidad Movimiento.
 * Este controlador utiliza los servicios IMovimientoService y ICuentaService para realizar las operaciones en la base de datos.
 */
@RestController
@RequestMapping("/api")
public class MovimientosRestController {

    @Autowired
    private IMovimientoService movimientoService;

    @Autowired
    private ICuentaService cuentaService;

    final Logger log = LoggerFactory.getLogger(MovimientosRestController.class.getName());


    /**
     * Retorna una lista con todos los movimientos en la base de datos.
     *
     * @return lista de movimientos.
     */
    @GetMapping("/movimientos")
    public List<Movimiento> index() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("Init Consume service {MovimientoServiceConAll};1;CON", "IN"));
        return movimientoService.findAll();
    }

    /**
     * Retorna un movimiento por su id.
     * Si el movimiento no existe, retorna una respuesta con estado HTTP 404 (NOT_FOUND).
     * Si ocurre un error al realizar la consulta, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param id identificador del movimiento a buscar.
     * @return movimiento encontrado.
     */
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) throws UnknownHostException, BadRequestExceptions {
        Movimiento movimiento = null;
        Map<String, Object> response = new HashMap<>();

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {movimentoServiceCon};1;CON", "IN"));


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

    /**
     * Crea un nuevo movimiento en la base de datos.
     * Si ocurre un error al realizar el insert, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param movimiento movimiento a crear.
     * @return movimiento creado.
     */
    @PostMapping("/movimientos")
    public ResponseEntity<?> create(@RequestBody MovimientoReq movimiento) throws BadRequestExceptions, UnknownHostException {
        Movimiento movimientoNew = null;
        Cuenta cuenta = null;
        Map<String, Object> response = new HashMap<>();

        if (null == movimiento){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }
        log.info(LoggerController.formatLoggerRst("Init Consume service {cuentaServiceCreate};1;MAN;", "IN"));

        try {
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
    /**
     * Actualiza una movimiento existente en la base de datos.
     * Si ocurre un error al actualizar el movimiento, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param id     identificador del movimiento a actualizar.
     * @return movimiento actualizado.
     */
    @PutMapping("/movimientos/{id}")
    public ResponseEntity<?> update(@RequestBody Movimiento movimiento, @PathVariable Long id) throws BadRequestExceptions, UnknownHostException {

        Movimiento movimientoUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (null == movimiento){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {cuentaServiceUpdate};1;MAN;", "IN"));

        try {
            movimientoUpdated = movimientoService.update(movimiento, id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el Movimiento en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "Movimiento ha sido actualizado con exíto !");
        response.put("Movimiento", movimientoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    /**
     * Elimina el movimiento del client
     *
     * @param id identificador del movimiento a eliminar
     */
    @DeleteMapping("/movimientos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) throws UnknownHostException, BadRequestExceptions {
        Map<String, Object> response = new HashMap<>();
        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }
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
