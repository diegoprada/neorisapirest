package com.neoris.backend.apirest.controllers;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neoris.backend.apirest.domain.models.CuentaReq;
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
import com.neoris.backend.apirest.service.ICuentaService;

/**
 * Controlador REST para manejar las operaciones CRUD sobre la entidad Cuenta.
 * Este controlador utiliza el servicio ICuentaService para realizar las operaciones en la base de datos.
 */
@RestController
@RequestMapping("/api")
public class CuentaRestController {

    @Autowired
    private ICuentaService cuentaService;
    final Logger log = LoggerFactory.getLogger(CuentaRestController.class.getName());
    /**
     * Retorna una lista con todas las cuentas en la base de datos.
     *
     * @return lista de cuentas.
     */
    @GetMapping("/cuentas")
    public List<Cuenta> index() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("Init Consume service {CuentaServiceConAll};1;CON", "IN"));
        return cuentaService.findAll();
    }

    /**
     * Retorna una cuenta por su id.
     * Si la cuenta no existe, retorna una respuesta con estado HTTP 404 (NOT_FOUND).
     * Si ocurre un error al realizar la consulta, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param id identificador de la cuenta a buscar.
     * @return cuenta encontrada.
     */
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) throws UnknownHostException, BadRequestExceptions {
        Cuenta cuenta = null;
        Map<String, Object> response = new HashMap<>();

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {cuentaServiceCon};1;CON", "IN"));

        try {
            cuenta = cuentaService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cuenta == null) {
            response.put("mensaje", "El Cuenta ID:".concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cuenta>(cuenta, HttpStatus.OK);
    }

    /**
     * Crea una nueva cuenta en la base de datos.
     * Si ocurre un error al realizar el insert, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param cuenta cuenta a crear.
     * @return cuenta creada.
     */
    @PostMapping("/cuentas")
    public ResponseEntity<?> create(@RequestBody CuentaReq cuenta) throws UnknownHostException, BadRequestExceptions {
        Cuenta cuentaNew = null;
        Map<String, Object> response = new HashMap<>();

        if (null == cuenta){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }
        log.info(LoggerController.formatLoggerRst("Init Consume service {cuentaServiceCreate};1;MAN;", "IN"));

        try {

            cuentaNew = cuentaService.save(cuenta);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "Cuenta ha sido creado con exíto !");
        response.put("Cuenta", cuentaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza una cuenta existente en la base de datos.
     * Si la cuenta no existe, retorna una respuesta con estado HTTP 404 (NOT_FOUND).
     * Si ocurre un error al actualizar la cuenta, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param cuenta cuenta con los nuevos datos a actualizar.
     * @param id     identificador de la cuenta a actualizar.
     * @return cuenta actualizada.
     */
    @PutMapping("/cuentas/{id}")
    public ResponseEntity<?> update(@RequestBody CuentaReq cuenta, @PathVariable Long id) throws UnknownHostException, BadRequestExceptions {
        Cuenta cuentaUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (null == cuenta){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {cuentaServiceUpdate};1;MAN;", "IN"));

        try {
            cuentaUpdated = cuentaService.update(cuenta,id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el Cuenta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Cuenta ha sido actualizado con exíto !");
        response.put("Cuenta", cuentaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina la cuenta del client
     *
     * @param id identificador de la  cuenta  a eliminar
     */
    @DeleteMapping("/cuentas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) throws UnknownHostException {
        Map<String, Object> response = new HashMap<>();
        try {
            cuentaService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el Cuenta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);// 404

        }
        response.put("mensaje", "El Cuenta eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
