package com.neoris.backend.apirest.domain.entity;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.*;

/**
 * La clase Movimiento representa un movimiento en una cuenta bancaria. Contiene
 * información sobre la fecha, tipo de movimiento, valor, saldo y cuenta
 * asociada.
 * 
 * @author: DIEGO PRADA
 * @version: 06/05/2023/A
 */
@Entity
@Table(name = "movimientos")
public class Movimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "La fecha no puede ser nula")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@NotBlank(message = "El tipo de movimiento no puede estar vacío")
	private String tipoMovimiento;
	@NotNull(message = "El valor no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor que 0")
	private double valor;
	@NotNull(message = "El saldo no puede ser nulo")
	private double saldo;

	@ManyToOne
	@JoinColumn(name = "cuenta_id")
	private Cuenta cuenta;

	/**
	 * Obtiene el identificador del movimiento.
	 *
	 * @return El identificador del movimiento.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del movimiento.
	 *
	 * @param id El identificador del movimiento a establecer.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene la fecha del movimiento.
	 *
	 * @return La fecha del movimiento.
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del movimiento.
	 *
	 * @param fecha La fecha del movimiento a establecer.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el tipo de movimiento.
	 *
	 * @return El tipo de movimiento.
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * Establece el tipo de movimiento.
	 *
	 * @param tipoMovimiento El tipo de movimiento a establecer.
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * Obtiene el valor del movimiento.
	 *
	 * @return El valor del movimiento.
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Establece el valor del movimiento.
	 *
	 * @param valor El valor del movimiento a establecer.
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el saldo después del movimiento.
	 *
	 * @return El saldo después del movimiento.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Establece el saldo después del movimiento.
	 *
	 * @param saldo El saldo después del movimiento a establecer.
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;

	}

	/**
	 * Obtiene la cuenta asociada al movimiento.
	 *
	 * @return La cuenta asociada al movimiento.
	 */
	public Cuenta getCuenta() {
		return cuenta;
	}

	/**
	 * Establece la cuenta asociada al movimiento.
	 *
	 * @param cuenta La cuenta asociada al movimiento a establecer.
	 */
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

}