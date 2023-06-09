package com.neoris.backend.apirest.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * La clase Cuenta representa una cuenta en el sistema. Contiene información
 * sobre el número de cuenta, tipo de cuenta, saldo inicial, estado y cliente
 * asociado.
 * 
 * @author: DIEGO PRADA
 * @version: 06/05/2023/A
 */
@Entity
@Table(name = "cuentas")
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	@NotNull(message = "El número de cuenta no puede ser nulo")
	@Min(value = 1, message = "El número de cuenta debe ser mayor o igual a 1")
	private Long numeroCuenta;
	@NotBlank(message = "El tipo de cuenta no puede estar vacío")
	private String tipoCuenta;
	@NotNull(message = "El saldo inicial no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = false, message = "El saldo inicial debe ser mayor que 0")
	private double saldoInicial;
	@NotBlank(message = "El estado no puede estar vacío")
	private String estado;
	@JoinColumn(name = "id_cliente", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Cliente idCliente;

	public Cuenta() {
	}

	public Cuenta(Long id, Long numeroCuenta, String tipoCuenta, double saldoInicial, String estado, Cliente idCliente) {
		this.id = id;
		this.numeroCuenta = numeroCuenta;
		this.tipoCuenta = tipoCuenta;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene el identificador de la cuenta.
	 *
	 * @return El identificador de la cuenta.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador de la cuenta.
	 *
	 * @param id El identificador de la cuenta a establecer.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el número de cuenta.
	 *
	 * @return El número de cuenta.
	 */
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Establece el número de cuenta.
	 *
	 * @param numeroCuenta El número de cuenta a establecer.
	 */
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * Obtiene el tipo de cuenta.
	 *
	 * @return El tipo de cuenta.
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * Establece el tipo de cuenta.
	 *
	 * @param tipoCuenta El tipo de cuenta a establecer.
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * Obtiene el saldo inicial de la cuenta.
	 *
	 * @return El saldo inicial de la cuenta.
	 */
	public double getSaldoInicial() {
		return saldoInicial;
	}

	/**
	 * Establece el saldo inicial de la cuenta.
	 *
	 * @param saldoInicial El saldo inicial de la cuenta a establecer.
	 */
	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	/**
	 * Obtiene el estado de la cuenta.
	 *
	 * @return El estado de la cuenta.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el estado de la cuenta.
	 *
	 * @param estado El estado de la cuenta a establecer.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene el cliente asociado a la cuenta.
	 *
	 * @return El cliente asociado a la cuenta.
	 */
	public Cliente getIdCliente() {
		return idCliente;
	}

	/**
	 * Establece el cliente asociado a la cuenta.
	 *
	 * @param idCliente El cliente asociado a la cuenta a establecer.
	 */
	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

}
