package com.neoris.backend.apirest.models.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * La clase Persona representa una persona con información personal básica.
 * Contiene atributos como nombre, género, edad, identificación, dirección y teléfono.
 * @author: DIEGO PRADA
 * @version: 06/05/2023/A
 */
@Entity 
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nombre;
	private String genero;
	private int edad;
	@Column(nullable = false, unique = true)
	private String identificacion;
	private String direccion;
	private String telefono;
	
	
	/**
	 * Crea una nueva instancia de la clase Persona sin inicializar los atributos.
	 */
	public Persona() {
		super();
	}
	/**
	 * Crea una nueva instancia de la clase Persona con los atributos especificados.
	 *
	 * @param nombre         El nombre de la persona.
	 * @param genero         El género de la persona.
	 * @param edad           La edad de la persona.
	 * @param identificacion La identificación de la persona.
	 * @param direccion      La dirección de la persona.
	 * @param telefono       El teléfono de la persona.
	 */
	public Persona(String nombre, String genero, int edad, String identificacion, String direccion, String telefono) {
		this.nombre = nombre;
		this.genero = genero;
		this.edad = edad;
		this.identificacion = identificacion;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	/**
	 * Obtiene el identificador de la persona.
	 *
	 * @return El identificador de la persona.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Establece el identificador de la persona.
	 *
	 * @param id El identificador de la persona a establecer.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Obtiene el nombre de la persona.
	 *
	 * @return El nombre de la persona.
	 */	
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece el nombre de la persona.
	 *
	 * @param nombre El nombre de la persona a establecer.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Obtiene el género de la persona.
	 *
	 * @return El género de la persona.
	 */
	public String getGenero() {
		return genero;
	}
	/**
	 * Establece el género de la persona.
	 *
	 * @param genero El género de la persona a establecer.
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	/**
	 * Obtiene la edad de la persona.
	 *
	 * @return La edad de la persona.
	 */
	public int getEdad() {
		return edad;
	}
	/**
	 * Establece la edad de la persona.
	 *
	 * @param edad La edad de la persona a establecer.
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}
	/**
	 * Obtiene la identificación de la persona.
	 *
	 * @return La identificación de la persona.
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	/**
	 * Establece la identificación de la persona.
	 *
	 * @return La identificación de la persona.
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * Obtiene la Direccion de la persona.
	 *
	 * direccion La Direccion de la persona.
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * Establece la Direccion de la persona.
	 *
	 * @return La Direccion de la persona.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * Obtiene la telefono de la persona.
	 *
	 * direccion La telefono de la persona.
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Obtiene la Telefono de la persona.
	 *
	 * direccion La Telefono de la persona.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	


}
