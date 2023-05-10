package com.neoris.backend.apirest.service.impl;

import java.net.UnknownHostException;
import java.util.List;

import com.neoris.backend.apirest.domain.models.CuentaReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.service.ICuentaService;
import com.neoris.backend.apirest.util.LoggerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.backend.apirest.repository.CuentaRepository;
import com.neoris.backend.apirest.domain.entity.Cuenta;

@Service
public class CuentaServiceImpl implements ICuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class.getName());

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository=cuentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished find ALL cuentas service", "OUT"));
        return (List<Cuenta>) cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished find ID cuenta ServiceCon", "OUT"));
        return cuentaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cuenta save(CuentaReq cuenta) throws UnknownHostException {
        Cuenta cuentaNew= new Cuenta();
        cuentaNew.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaNew.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaNew.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaNew.setEstado(cuenta.getEstado());
        cuentaNew.setIdCliente(cuenta.getIdCliente());

        log.info(LoggerController.formatLoggerRst("finished create cuenta service", "OUT"));
        return cuentaRepository.save(cuentaNew);
    }

    @Override
    @Transactional
    public void delete(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished delete cuenta service", "OUT"));
        cuentaRepository.deleteById(id);
    }

    @Override
    public Cuenta findByNumeroCuenta(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished find cuenta service", "OUT"));
        return cuentaRepository.findByNumeroCuenta(id);
    }

    @Override
    public Cuenta update(CuentaReq cuenta, Long id) throws UnknownHostException, BadRequestExceptions {
        Cuenta cuentaActual = cuentaRepository.findById(id).orElse(null);

        if (cuentaActual == null) {
            throw new BadRequestExceptions("400", "LA CUENTA NO EXISTE ", "CHECK DATA", "ERROR", 400);
        }
        cuentaActual.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaActual.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaActual.setEstado(cuenta.getEstado());
        cuentaActual.setIdCliente(cuenta.getIdCliente());

        log.info(LoggerController.formatLoggerRst("finished update cuenta service", "OUT"));

        return cuentaRepository.save(cuentaActual);
    }

    @Override
    public double revisarSaldo(long cuentaId) throws UnknownHostException {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElse(null);
        log.info(LoggerController.formatLoggerRst("finished check saldo service", "OUT"));
        return cuenta.getSaldoInicial();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, Integer monto) throws UnknownHostException {
        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElse(null);
        cuentaOrigen.setSaldoInicial(cuentaOrigen.getSaldoInicial() - monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElse(null);
        cuentaDestino.setSaldoInicial(cuentaDestino.getSaldoInicial()+monto);
        cuentaRepository.save(cuentaDestino);
        log.info(LoggerController.formatLoggerRst("finished transferir saldo service", "OUT"));

    }
}
