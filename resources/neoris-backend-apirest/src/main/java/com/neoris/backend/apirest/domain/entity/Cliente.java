
package com.neoris.backend.apirest.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.validation.constraints.*;

/**
 * La clase Cliente representa a un cliente en el sistema.
 * Extiende la clase Persona y agrega atributos y relaciones específicas del cliente.* 
 * @author: DIEGO PRADA
 * @version: 06/05/2023/A
 */

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {
	@Id
	private Long id;
	@NotBlank(message = "La contraseña no puede estar vacía")
	@Size(min = 6, max = 20, message = "La contraseña debe tener entre 3 y 20 caracteres")
	private String contrasena;
	@NotBlank(message = "El estado no puede estar vacío")
	private String estado;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
	private Collection<Cuenta> cuentasCollection;
	
	
	/**
	 * Crea una instancia de Cliente.
	 */
	public Cliente() {
		super();
	}
	
	/**
	 * Crea una instancia de Cliente con los datos especificados.
	 *
	 * @param nombre        El nombre del cliente.
	 * @param genero        El género del cliente.
	 * @param edad          La edad del cliente.
	 * @param identificacion La identificación del cliente.
	 * @param direccion     La dirección del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param contrasena    La contraseña del cliente.
	 * @param estado        El estado del cliente.
	 */
	public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono,
			 String contrasena, String estado) {
		super(nombre, genero, edad, identificacion, direccion, telefono);
		this.contrasena = contrasena;
		this.estado = estado;
	}
	
	
	/**
	 * Obtiene el identificador del cliente.
	 *
	 * @return El identificador del cliente.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Establece el identificador del cliente.
	 *
	 * @param id El identificador del cliente a establecer.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Obtiene la contraseña del cliente.
	 *
	 * @return La contraseña del cliente.
	 */
	public String getContrasena() {
		return contrasena;
	}
	
	/**
	 * Establece la contraseña del cliente.
	 *
	 * @param contrasena La contraseña del cliente a establecer.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	/**
	 * Obtiene el estado del cliente.
	 *
	 * @return El estado del cliente.
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Establece el estado del cliente.
	 *
	 * @param estado El estado del cliente a establecer.
	 */

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
