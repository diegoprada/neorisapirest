package com.neoris.backend.apirest.service.impl;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import com.neoris.backend.apirest.domain.entity.Cliente;
import com.neoris.backend.apirest.domain.models.ClienteReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.service.IClienteService;
import com.neoris.backend.apirest.util.LoggerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.backend.apirest.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    final
    Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class.getName());

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished consulta clientes service", "OUT"));
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished cliente ServiceCon", "OUT"));
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente save(ClienteReq cliente) throws UnknownHostException {
        Cliente clienteNew = new Cliente();
        clienteNew.setNombre(cliente.getNombre());
        clienteNew.setGenero(cliente.getGenero());
        clienteNew.setEdad(cliente.getEdad());
        clienteNew.setIdentificacion(cliente.getIdentificacion());
        clienteNew.setDireccion(cliente.getDireccion());
        clienteNew.setTelefono(cliente.getTelefono());
        clienteNew.setContrasena(cliente.getContrasena());
        clienteNew.setEstado(cliente.getEstado());
        log.info(LoggerController.formatLoggerRst("finished create cliente service", "OUT"));
        return clienteRepository.save(clienteNew);
    }

    @Override
    @Transactional
    public void delete(Long id) throws UnknownHostException {
        clienteRepository.deleteById(id);
        log.info(LoggerController.formatLoggerRst("finished delete cliente service", "OUT"));
    }

    @Override
    @Transactional
    public Cliente update(ClienteReq cliente, Long id) throws BadRequestExceptions, UnknownHostException {
        Cliente clienteActual = clienteRepository.findById(id).orElse(null);

        if (clienteActual == null) {
            throw new BadRequestExceptions("400", "EL CIENTE NO EXISTE ", "CHECK DATA", "ERROR", 400);
        }
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setGenero(cliente.getGenero());
        clienteActual.setEdad(cliente.getEdad());
        clienteActual.setDireccion(cliente.getDireccion());
        clienteActual.setTelefono(cliente.getTelefono());
        clienteActual.setEstado(cliente.getEstado());

        log.info(LoggerController.formatLoggerRst("finished update cliente service", "OUT"));
        return clienteRepository.save(clienteActual);
    }

}
