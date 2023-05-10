package com.neoris.backend.apirest.controllers;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.models.ClienteReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.service.impl.ClienteServiceImpl;
import com.neoris.backend.apirest.util.LoggerController;
import com.neoris.backend.apirest.util.ValidateTypeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.backend.apirest.service.IClienteService;

/**
 * Controlador REST para manejar las operaciones CRUD sobre la entidad Cliente.
 * Este controlador utiliza el servicio IClienteService para realizar las operaciones en la base de datos.
 */
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    final Logger log = LoggerFactory.getLogger(ClienteRestController.class.getName());

    /**
     * Retorna una lista con todos los clientes en la base de datos.
     *
     * @return lista de clientes.
     */
    @GetMapping("/clientes")
    public List<Cliente> index() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("Init Consume service {clienteServiceConAll};1;CON", "IN"));
        return clienteService.findAll();
    }

    /**
     * Retorna un cliente por su id.
     * <p>
     * Si el cliente no existe, retorna una respuesta con estado HTTP 404 (NOT_FOUND)
     * Si ocurre un error al realizar la consulta, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param id identificador del cliente a buscar.
     * @return cliente encontrado.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) throws UnknownHostException, BadRequestExceptions {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {clienteServiceCon};1;CON", "IN"));

        try {
            cliente = clienteService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente ID:".concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     * Si ocurre un error al realizar el insert, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param cliente cliente a crear.
     * @return cliente creado.
     */
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody ClienteReq cliente) throws BadRequestExceptions, UnknownHostException {
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        if (null == cliente){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {clienteServiceCreate};1;CON", "IN"));

        try {

            clienteNew = clienteService.save(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "cliente ha sido creado con exíto !");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un cliente existente en la base de datos.
     * Si el cliente no existe, retorna una respuesta con estado HTTP 404 (NOT_FOUND).
     * Si ocurre un error al actualizar el cliente, retorna una respuesta con estado HTTP 500 (INTERNAL_SERVER_ERROR)
     *
     * @param cliente cliente con los nuevos datos a actualizar.
     * @param id      identificador del cliente a actualizar.
     * @return cliente actualizado.
     */
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteReq cliente, @PathVariable Long id) throws BadRequestExceptions, UnknownHostException {
        Cliente clienteUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (null == cliente){
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR BODY", "INFO", 400);
        }

        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }

        log.info(LoggerController.formatLoggerRst("Init Consume service {clienteServiceUpdate};1;CON", "IN"));

        try {
            clienteUpdated = clienteService.update(cliente, id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "cliente ha sido actualizado con exíto !");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina el cliente por el identificador del cliente
     *
     * @param id identificador del cliente a eliminar
     */
    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) throws BadRequestExceptions, UnknownHostException {
        Map<String, Object> response = new HashMap<>();
        if (null == id) {
            throw new BadRequestExceptions("400", "INVALID REQUEST", "VALIDATE YOUR PARAMETERS ID", "INFO", 400);
        }
        log.info(LoggerController.formatLoggerRst("Init Consume service {clienteServiceDelete};1;CON", "IN"));

        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "El cliente eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
