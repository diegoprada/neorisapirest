package com.neoris.backend.apirest.service.impl;


import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.neoris.backend.apirest.domain.entity.Cuenta;
import com.neoris.backend.apirest.domain.models.MovimientoReq;
import com.neoris.backend.apirest.exceptions.BadRequestExceptions;
import com.neoris.backend.apirest.repository.CuentaRepository;
import com.neoris.backend.apirest.service.IMovimientoService;
import com.neoris.backend.apirest.util.LoggerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.backend.apirest.repository.MovimientoRepository;
import com.neoris.backend.apirest.domain.entity.Movimiento;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
    Logger log = LoggerFactory.getLogger(MovimientoServiceImpl.class.getName());


    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> findAll() throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished consulta movimientos service", "OUT"));
        return (List<Movimiento>) movimientoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Movimiento findById(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished consulta movimiento ServiceCon", "OUT"));
        return movimientoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Movimiento save(MovimientoReq movimiento) throws UnknownHostException, BadRequestExceptions {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        Movimiento movNew = new Movimiento();

        if (cuenta.getSaldoInicial() == 0 && movimiento.getValor() < 0) {
            throw new BadRequestExceptions("400", "SALDO NO DISPONIBLE", "CHECK DATA", "ERROR", 400);
        }

        if (movimiento.getValor() < 0) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - (movimiento.getValor() * -1));
        } else {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());

        }
        movNew.setFecha(movimiento.getFecha());
        movNew.setTipoMovimiento(movimiento.getTipoMovimiento());
        movNew.setValor(movimiento.getValor());
        movNew.setSaldo(movimiento.getSaldo());
        movNew.setCuenta(movimiento.getCuenta());
        ///
        movNew.setCuenta(cuenta);
        movNew.setSaldo(cuenta.getSaldoInicial());

        log.info(LoggerController.formatLoggerRst("finished create movimiento service", "OUT"));
        return movimientoRepository.save(movNew);
    }

    @Override
    @Transactional
    public void delete(Long id) throws UnknownHostException {
        log.info(LoggerController.formatLoggerRst("finished delete movimiento service", "OUT"));
        movimientoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> findByIdfstartFend(Long id, Date fstart, Date fend) throws UnknownHostException, BadRequestExceptions {
        List<Movimiento> reporte = movimientoRepository.findByIdAndfindAllByFechaBetween(id, fstart, fend);
        if (reporte == null) {
            throw new BadRequestExceptions("400", "NO HAY MOVIMIENTO  EXISTENTES ", "CHECK DATA", "ERROR", 400);
        }

        log.info(LoggerController.formatLoggerRst("finished reporte service", "OUT"));
        return reporte;
    }

    @Override
    public Movimiento update(Movimiento movimiento, Long id) throws BadRequestExceptions, UnknownHostException {
        Movimiento movimientoActual = movimientoRepository.findById(id).orElse(null);

        if (movimientoActual == null) {
            throw new BadRequestExceptions("400", "EL MOVIMIENTO NO EXISTE ", "CHECK DATA", "ERROR", 400);
        }
        movimientoActual.setFecha(movimiento.getFecha());
        movimientoActual.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoActual.setValor(movimiento.getValor());
        movimientoActual.setSaldo(movimiento.getSaldo());
        movimientoActual.setCuenta(movimiento.getCuenta());

        log.info(LoggerController.formatLoggerRst("finished update movimiento service", "OUT"));
        return movimientoRepository.save(movimientoActual);
    }

}
